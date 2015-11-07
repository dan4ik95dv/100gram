package me.stogram.android.util;

import com.vk.sdk.VKScope;

/**
 * Created by Daniil Celikin on 06.11.2015.
 */
public class Constants {
    public static class Config {
        public static final String CONFIG = "Config";
        public static final String TOKEN = "TOKEN";
    }


    public static class VK {
        public static final String[] MyScopes = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.EMAIL, VKScope.OFFLINE};
        public static final int APP_ID = 5136311;
    }
    public static class API {
        public static final String BASE_URL = "http://192.168.1.7:8000/api/";

    }
}
