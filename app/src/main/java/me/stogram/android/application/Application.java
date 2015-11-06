package me.stogram.android.application;

import android.util.Log;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

/**
 * Created by Daniil Celikin on 05.11.2015.
 */
public class Application extends android.app.Application {
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        }

    };
    @Override public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        LeakCanary.install(this);
    }

}
