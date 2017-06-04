package com.i2nexted.mvpframe.util;

import android.net.Uri;

import com.i2nexted.mvpframe.app.MyApplication;

/**
 * Created by Administrator on 2017/5/24.
 */

public class UriUtil {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    /**
     * 将项目中的资源id转化成uri
     * */
    public static Uri resourceIdToUri( int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + MyApplication.getMyApplication().getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
