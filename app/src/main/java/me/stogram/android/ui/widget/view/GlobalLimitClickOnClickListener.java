package me.stogram.android.ui.widget.view;

import android.view.View;
import android.view.View.OnClickListener;


/**
 * 全局拒绝频繁点击代理Listener
 * 
 * @author tongqian.ni
 */
public class GlobalLimitClickOnClickListener implements OnClickListener {

    private static long     lastClick;

    private OnClickListener listener;

    private long            intervalClick;

    public GlobalLimitClickOnClickListener(OnClickListener listener, long intervalClick) {
        this.intervalClick = intervalClick;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() > lastClick
            && System.currentTimeMillis() - lastClick <= intervalClick) {
            return;
        }
        listener.onClick(v);
        lastClick = System.currentTimeMillis();
    }
}
