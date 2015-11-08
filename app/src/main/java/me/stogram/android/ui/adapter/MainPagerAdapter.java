package me.stogram.android.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import me.stogram.android.R;

import me.stogram.android.ui.fragment.FeedFragment;
import me.stogram.android.ui.fragment.ProfileFragment;

public class MainPagerAdapter extends CacheFragmentStatePagerAdapter {
	private int[] imageResId = {
			R.drawable.ic_view_day_white_36dp,
			R.drawable.ic_account_box_white_36dp
	};


	public static final int FEED_TAB = 0;
	public static final int PROFILE_TAB = 1;

	private final Context context;

	public MainPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case FEED_TAB:
				return FeedFragment.init();
			case PROFILE_TAB:
				return ProfileFragment.init();
		}
		return null;
	}

	@Override
	public String getItemTag(int position) {
		switch (position) {
			case FEED_TAB:
				return "feed";
			case PROFILE_TAB:
				return "profile";
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
		image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
		SpannableString sb = new SpannableString(" ");
		ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
		sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sb;
	}

	@Override
	public int getCount() {
		return 2;
	}

	public Fragment getItemAt(int position) {
		return mFragmentManager.findFragmentByTag(getItemTag(position));
	}
}
