package me.stogram.android.ui.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import me.stogram.android.ui.base.util.DialogHelper;


public class ActivityHelper {
    final static String TAG = ActivityHelper.class.getSimpleName();

    private Activity mActivity;

    private DialogHelper mDialogHelper;

    public ActivityHelper(Activity activity) {
        mActivity = activity;
        mDialogHelper = new DialogHelper(mActivity);
    }

    public void finish() {
        mDialogHelper.dismissProgressDialog();
    }

    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener) {
        mDialogHelper.alert(title, msg, positive, positiveListener, negative, negativeListener);
    }

    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener,
                      Boolean isCanceledOnTouchOutside) {
        mDialogHelper.alert(title, msg, positive, positiveListener, negative, negativeListener,
                isCanceledOnTouchOutside);
    }


    public void toast(String msg, int period) {
        mDialogHelper.toast(msg, period);
    }

    public void showProgressDialog(String msg) {
        mDialogHelper.showProgressDialog(msg);
    }

    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener) {
        mDialogHelper.showProgressDialog(msg, cancelable, cancelListener, true);
    }

    public void dismissProgressDialog() {
        mDialogHelper.dismissProgressDialog();
    }

}
