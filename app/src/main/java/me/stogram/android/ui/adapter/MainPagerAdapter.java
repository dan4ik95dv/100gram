package me.stogram.android.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import me.stogram.android.R;
import me.stogram.android.ui.fragment.CameraFragment;
import me.stogram.android.ui.fragment.FeedFragment;
import me.stogram.android.ui.fragment.ProfileFragment;

public class MainPagerAdapter extends CacheFragmentStatePagerAdapter {

	public static final int FEED_TAB = 0;
	public static final int CAMERA_TAB = 1;
	public static final int PROFILE_TAB = 2;

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
			case CAMERA_TAB:
				return CameraFragment.init();
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
			case CAMERA_TAB:
				return "camera";
			case PROFILE_TAB:
				return "profile";
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case CAMERA_TAB:
				return "Камера";
			case PROFILE_TAB:
				return "Профиль";
			case FEED_TAB:
				return "Лента";
		}
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

	public Fragment getItemAt(int position) {
		return mFragmentManager.findFragmentByTag(getItemTag(position));
	}
}
