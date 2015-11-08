package me.stogram.android.camera.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.widget.HListView;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.camera.CameraBaseActivity;
import me.stogram.android.camera.CameraManager;
import me.stogram.android.camera.EffectService;
import me.stogram.android.camera.adapter.FilterAdapter;
import me.stogram.android.camera.adapter.StickerToolAdapter;
import me.stogram.android.camera.effect.FilterEffect;
import me.stogram.android.camera.util.EffectUtil;
import me.stogram.android.camera.util.GPUImageFilterTools;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.templates.Template;
import me.stogram.android.model.templates.response.ResponseTemplates;
import me.stogram.android.ui.activity.CreatePostActivity;
import me.stogram.android.ui.widget.imagezoom.ImageViewTouch;
import me.stogram.android.ui.widget.view.MyHighlightView;
import me.stogram.android.ui.widget.view.MyImageViewDrawableOverlay;
import me.stogram.android.util.Constants;
import me.stogram.android.util.FileUtils;
import me.stogram.android.util.ImageUtils;
import me.stogram.android.util.StringUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 图片处理界面
 * Created by sky on 2015/7/8.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class PhotoProcessActivity extends CameraBaseActivity {

    @Bind(R.id.gpuimage)
    GPUImageView mGPUImageView;
    @Bind(R.id.drawing_view_container)
    ViewGroup drawArea;
    @Bind(R.id.sticker_btn)
    LinearLayout stickerBtn;
    @Bind(R.id.filter_btn)
    LinearLayout filterBtn;
    @Bind(R.id.list_tools)
    HListView bottomToolBar;
    @Bind(R.id.toolbar_area)
    ViewGroup toolArea;
    private MyImageViewDrawableOverlay mImageView;

    private LinearLayout currentBtn;
    private Bitmap currentBitmap;
    private Bitmap smallImageBackgroud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        ButterKnife.bind(this);
        EffectUtil.clear();
        initView();
        initEvent();
        getTemplates();

        ImageUtils.asyncLoadImage(this, getIntent().getData(), new ImageUtils.LoadImageCallback() {
            @Override
            public void callback(Bitmap result) {
                currentBitmap = result;
                mGPUImageView.setImage(currentBitmap);
            }
        });

        ImageUtils.asyncLoadSmallImage(this, getIntent().getData(), new ImageUtils.LoadImageCallback() {
            @Override
            public void callback(Bitmap result) {
                smallImageBackgroud = result;
            }
        });

    }

    private void initView() {
        View overlay = LayoutInflater.from(PhotoProcessActivity.this).inflate(
                R.layout.view_drawable_overlay, null);
        mImageView = (MyImageViewDrawableOverlay) overlay.findViewById(R.id.drawable_overlay);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(App.getApp().getScreenWidth(),
                App.getApp().getScreenWidth());
        mImageView.setLayoutParams(params);
        overlay.setLayoutParams(params);
        drawArea.addView(overlay);

        RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(App.getApp().getScreenWidth(), App.getApp().getScreenWidth());
        mGPUImageView.setLayoutParams(rparams);

    }

    private void getTemplates() {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ResponseTemplates> call = apiService.getTemplates(App.getToken());
        call.enqueue(new Callback<ResponseTemplates>() {
            @Override
            public void onResponse(Response<ResponseTemplates> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<Template> templates = response.body().getTemplate();
                    if (templates != null) {
                        initStickerToolBar(templates);
                    }
                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initEvent() {
        stickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setCurrentBtn(stickerBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);

                getTemplates();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setCurrentBtn(filterBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);

                initFilterToolBar();
            }
        });

        mImageView.setOnDrawableEventListener(wpEditListener);
        mImageView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                drawArea.postInvalidate();
            }
        });
        titleBar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture();
            }
        });
    }

    //Сохранение фотографии
    private void savePicture() {
        final Bitmap newBitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        RectF dst = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
        try {
            cv.drawBitmap(mGPUImageView.capture(), null, dst, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            cv.drawBitmap(currentBitmap, null, dst, null);
        }
        EffectUtil.applyOnSave(cv, mImageView);

        new SavePicToFileTask().execute(newBitmap);
    }

    private class SavePicToFileTask extends AsyncTask<Bitmap, Void, String> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(getString(R.string.processing_wait));
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            String fileName = null;
            try {
                bitmap = params[0];

                String picName = Long.toString(new Date().getTime());
                fileName = ImageUtils.saveToFile(FileUtils.getInst().getPhotoSavedPath() + "/" + picName, false, bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                toast(getString(R.string.camera_error), Toast.LENGTH_LONG);
            }
            return fileName;
        }

        @Override
        protected void onPostExecute(String fileName) {
            super.onPostExecute(fileName);
            dismissProgressDialog();
            if (StringUtils.isEmpty(fileName)) {
                return;
            }
            Intent i = new Intent(PhotoProcessActivity.this, CreatePostActivity.class);
            i.putExtra("PATH_FILE", fileName);
            startActivity(i);
            CameraManager.getInst().close();
        }
    }


    private MyImageViewDrawableOverlay.OnDrawableEventListener wpEditListener = new MyImageViewDrawableOverlay.OnDrawableEventListener() {
        @Override
        public void onMove(MyHighlightView view) {
        }

        @Override
        public void onFocusChange(MyHighlightView newFocus, MyHighlightView oldFocus) {
        }

        @Override
        public void onDown(MyHighlightView view) {

        }

        @Override
        public void onClick(MyHighlightView view) {
        }


    };

    private boolean setCurrentBtn(LinearLayout btn) {
        if (currentBtn == null) {
            currentBtn = btn;
        } else if (currentBtn.equals(btn)) {
            return false;
        }
        currentBtn = btn;
        return true;
    }


    private void initStickerToolBar(final List<Template> templateList) {
        bottomToolBar.setAdapter(new StickerToolAdapter(PhotoProcessActivity.this, templateList));
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0,
                                    View arg1, int arg2, long arg3) {
                final Template sticker = templateList.get(arg2);
                Glide.with(getBaseContext()).load(Constants.API.HOST_URL + sticker.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        EffectUtil.addStickerImage(mImageView, PhotoProcessActivity.this, sticker, resource,
                                new EffectUtil.StickerCallback() {
                                    @Override
                                    public void onRemoveSticker(Template sticker) {
                                    }
                                });
                        setCurrentBtn(stickerBtn);
                    }
                });
            }
        });
    }


    private void initFilterToolBar() {
        final List<FilterEffect> filters = EffectService.getInst().getLocalFilters();
        final FilterAdapter adapter = new FilterAdapter(PhotoProcessActivity.this, filters, smallImageBackgroud);
        bottomToolBar.setAdapter(adapter);
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (adapter.getSelectFilter() != arg2) {
                    adapter.setSelectFilter(arg2);
                    GPUImageFilter filter = GPUImageFilterTools.createFilterForType(
                            PhotoProcessActivity.this, filters.get(arg2).getType());
                    mGPUImageView.setFilter(filter);
                    GPUImageFilterTools.FilterAdjuster mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
                    if (mFilterAdjuster.canAdjust()) {
                        //mFilterAdjuster.adjust(100);
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
