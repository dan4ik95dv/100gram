package me.stogram.android.camera.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import me.stogram.android.R;
import me.stogram.android.model.templates.Template;
import me.stogram.android.util.Constants;

/**
 * 贴纸适配器
 *
 * @author tongqian.ni
 */
public class StickerToolAdapter extends BaseAdapter {

    List<Template> filterUris;
    Context mContext;

    public StickerToolAdapter(Context context, List<Template> effects) {
        filterUris = effects;
        mContext = context;
    }

    @Override
    public int getCount() {
        return filterUris.size();
    }

    @Override
    public Object getItem(int position) {
        return filterUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EffectHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_bottom_tool, null);
            holder = new EffectHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.effect_image);
            holder.container = (ImageView) convertView.findViewById(R.id.effect_background);
            //holder.navImage.setOnClickListener(holder.clickListener);
            convertView.setTag(holder);
        } else {
            holder = (EffectHolder) convertView.getTag();
        }

        final Template effect = (Template) getItem(position);

        return showItem(convertView, holder, effect);
    }

    private View showItem(View convertView, EffectHolder holder, final Template sticker) {
        holder.container.setVisibility(View.GONE);
        Glide.with(mContext).load(Constants.API.HOST_URL + sticker.getImage())
        .fitCenter().
        diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .into(holder.logo);

        return convertView;
    }

    class EffectHolder {
        ImageView logo;
        ImageView container;
    }

}
