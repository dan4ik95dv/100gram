package me.stogram.android.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carbon.widget.ImageView;
import me.stogram.android.R;
import me.stogram.android.camera.CameraManager;
import me.stogram.android.ui.adapter.MainPagerAdapter;


/**
 * Created by Daniil Celikin on 06.11.2015.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ButtonTakePhoto)
    ImageView mButtonTakePhoto;

    @Bind(R.id.profileButton)
    carbon.widget.LinearLayout profileButton;

    @Bind(R.id.feedButton)
    carbon.widget.LinearLayout feedButton;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;


    @OnClick(R.id.feedButton)
    void feedButtonOnClick(){
        mViewPager.setCurrentItem(mAdapter.FEED_TAB);
    }
    @OnClick(R.id.profileButton)
    void profileButtonOnClick(){
        mViewPager.setCurrentItem(mAdapter.PROFILE_TAB);
    }
    @OnClick(R.id.ButtonTakePhoto)
    void buttonTakePhotoOnClick(){
        CameraManager.getInst().openCamera(MainActivity.this);
    }

    public static String POSITION = "POSITION";
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);

    }

}