package me.stogram.android.util;

import android.os.Environment;

import com.vk.sdk.VKScope;

/**
 * Created by Daniil Celikin on 06.11.2015.
 */
public class Constants {

    public static final String APP_DIR                    = Environment.getExternalStorageDirectory() + "/StickerCamera";
    public static final String APP_TEMP                   = APP_DIR + "/temp";
    public static final String APP_IMAGE                  = APP_DIR + "/image";
    public static final int    POST_TYPE_POI              = 1;
    public static final int    POST_TYPE_TAG              = 0;
    public static final int    POST_TYPE_DEFAULT		  = 0;
    public static final float  DEFAULT_PIXEL              = 1242;                           //按iphone6设置
    public static final String PARAM_MAX_SIZE             = "PARAM_MAX_SIZE";
    public static final String PARAM_EDIT_TEXT            = "PARAM_EDIT_TEXT";
    public static final int    ACTION_EDIT_LABEL          = 8080;
    public static final int    ACTION_EDIT_LABEL_POI      = 9090;
    public static final String FEED_INFO                  = "FEED_INFO";
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;




    public static class Config {
        public static final String CONFIG = "Config";
        public static final String TOKEN = "TOKEN";
    }


    public static class VK {
        public static final String[] MyScopes = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.EMAIL, VKScope.OFFLINE};
        public static final int APP_ID = 5136311;
    }
    public static class API {
        public static final String BASE_URL = "http://192.168.1.5:8000/api/";
        public static final String HOST_URL = "http://192.168.1.5:8000";

    }
}
