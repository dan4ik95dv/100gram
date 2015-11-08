package me.stogram.android.application;

import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import me.stogram.android.util.Constants;

/**
 * Created by Daniil Celikin on 05.11.2015.
 */
public class App extends android.app.Application {
    private static SharedPreferences shPref;
    private static SharedPreferences.Editor editPref;
    private static String token;
    protected static App       mInstance;
    private DisplayMetrics     displayMetrics = null;

    public static SharedPreferences getPref() {
        return shPref;
    }
    public static SharedPreferences.Editor getEditPref() {
        return editPref;
    }

    public static String getToken(){
        if (token == null) {
            token = shPref.getString(Constants.Config.TOKEN,"");
        }
        return token;
    }

    public static void setToken(String token) {
        App.token = token;
        editPref.putString(Constants.Config.TOKEN,token);
        editPref.commit();
    }

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        }

    };
        public App(){
            mInstance = this;
        }

        public static App getApp() {
            if (mInstance != null && mInstance instanceof App) {
                return (App) mInstance;
            } else {
                mInstance = new App();
                mInstance.onCreate();
                return (App) mInstance;
            }
        }

        @Override
        public void onCreate() {
            super.onCreate();
            Stetho.initializeWithDefaults(this);
            vkAccessTokenTracker.startTracking();
            VKSdk.initialize(this);
            App.shPref = getSharedPreferences(Constants.Config.CONFIG, 0);
            App.editPref = shPref.edit();
            initImageLoader();
            mInstance = this;

        }


        private void initImageLoader() {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(false)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .cacheOnDisk(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .defaultDisplayImageOptions(defaultOptions)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, Constants.APP_IMAGE)))
                    .diskCacheSize(100 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
                    .threadPoolSize(3)
                    .build();
            ImageLoader.getInstance().init(config);
        }


        public float getScreenDensity() {
            if (this.displayMetrics == null) {
                setDisplayMetrics(getResources().getDisplayMetrics());
            }
            return this.displayMetrics.density;
        }

        public int getScreenHeight() {
            if (this.displayMetrics == null) {
                setDisplayMetrics(getResources().getDisplayMetrics());
            }
            return this.displayMetrics.heightPixels;
        }

        public int getScreenWidth() {
            if (this.displayMetrics == null) {
                setDisplayMetrics(getResources().getDisplayMetrics());
            }
            return this.displayMetrics.widthPixels;
        }

        public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
            this.displayMetrics = DisplayMetrics;
        }

        public int dp2px(float f)
        {
            return (int)(0.5F + f * getScreenDensity());
        }

        public int px2dp(float pxValue) {
            return (int) (pxValue / getScreenDensity() + 0.5f);
        }

        public String getFilesDirPath() {
            return getFilesDir().getAbsolutePath();
        }

        public String getCacheDirPath() {
            return getCacheDir().getAbsolutePath();
        }


}
