package me.stogram.android.application;

import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
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


    public App() {
        super();
    }

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

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        LeakCanary.install(this);
        App.shPref = getSharedPreferences(Constants.Config.CONFIG, 0);
        App.editPref = shPref.edit();
    }

}
