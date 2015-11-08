package me.stogram.android.ui.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public interface ActivityResponsable {

    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative, final DialogInterface.OnClickListener negativeListener);

    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative,
                      final DialogInterface.OnClickListener negativeListener,
                      Boolean isCanceledOnTouchOutside);


    public void toast(final String msg, final int period);

    public void showProgressDialog(final String msg);


    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener);

    public void dismissProgressDialog();

}
