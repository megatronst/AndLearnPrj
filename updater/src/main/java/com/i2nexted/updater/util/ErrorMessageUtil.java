package com.i2nexted.updater.util;

import android.text.TextUtils;
import android.util.SparseArray;

import com.i2nexted.updater.R;
import com.i2nexted.updater.util.Utils;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ErrorMessageUtil {
    public static final int UPDATE_IGNORED = 1001;
    public static final int UPDATE_NO_NEWER = 1002;

    public static final int CHECK_UNKNOWN = 2001;
    public static final int CHECK_NO_WIFI = 2002;
    public static final int CHECK_NO_NETWORK = 2003;
    public static final int CHECK_NETWORK_IO = 2004;
    public static final int CHECK_HTTP_STATUS = 2005;
    public static final int CHECK_PARSE = 2006;

    public static final int DOWNLOAD_UNKNOWN = 3001;
    public static final int DOWNLOAD_CANCELLED = 3002;
    public static final int DOWNLOAD_DISK_NO_SPACE = 3003;
    public static final int DOWNLOAD_DISK_IO = 3004;
    public static final int DOWNLOAD_NETWORK_IO = 3005;
    public static final int DOWNLOAD_NETWORK_BLOCKED = 3006;
    public static final int DOWNLOAD_NETWORK_TIMEOUT = 3007;
    public static final int DOWNLOAD_HTTP_STATUS = 3008;
    public static final int DOWNLOAD_INCOMPLETE = 3009;
    public static final int DOWNLOAD_VERIFY = 3010;

    public static final SparseArray<Integer> messages = new SparseArray<>();
    static {
        messages.append(UPDATE_IGNORED, R.string.ERROR_UPDATE_UPDATE_IGNORED);
        messages.append(UPDATE_NO_NEWER,R.string.ERROR_UPDATE_UPDATE_NO_NEWER);
        messages.append(CHECK_UNKNOWN,R.string.ERROR_UPDATE_CHECK_UNKNOWN);
        messages.append(CHECK_NO_WIFI,R.string.ERROR_UPDATE_CHECK_NO_WIFI);
        messages.append(CHECK_NO_NETWORK,R.string.ERROR_UPDATE_CHECK_NO_NETWORK);
        messages.append(CHECK_NETWORK_IO,R.string.ERROR_UPDATE_CHECK_NETWORK_IO);
        messages.append(CHECK_HTTP_STATUS,R.string.ERROR_UPDATE_CHECK_HTTP_STATUS);
        messages.append(CHECK_PARSE,R.string.ERROR_UPDATE_CHECK_PARSE);
        messages.append(DOWNLOAD_UNKNOWN,R.string.ERROR_UPDATE_DOWNLOAD_UNKNOWN);
        messages.append(DOWNLOAD_CANCELLED,R.string.ERROR_UPDATE_DOWNLOAD_CANCELLED);
        messages.append(DOWNLOAD_DISK_NO_SPACE,R.string.ERROR_UPDATE_DOWNLOAD_DISK_NO_SPACE);
        messages.append(DOWNLOAD_DISK_IO,R.string.ERROR_UPDATE_DOWNLOAD_DISK_IO);
        messages.append(DOWNLOAD_NETWORK_IO,R.string.ERROR_UPDATE_DOWNLOAD_NETWORK_IO);
        messages.append(DOWNLOAD_NETWORK_BLOCKED,R.string.ERROR_UPDATE_DOWNLOAD_NETWORK_BLOCKED);
        messages.append(DOWNLOAD_NETWORK_TIMEOUT,R.string.ERROR_UPDATE_DOWNLOAD_NETWORK_TIMEOUT);
        messages.append(DOWNLOAD_HTTP_STATUS,R.string.ERROR_UPDATE_DOWNLOAD_HTTP_STATUS);
        messages.append(DOWNLOAD_INCOMPLETE,R.string.ERROR_UPDATE_DOWNLOAD_INCOMPLETE);
        messages.append(DOWNLOAD_VERIFY,R.string.ERROR_UPDATE_DOWNLOAD_VERIFY);
    }

    /**
     * 判断对应code是否为错误类型
     * */
    public static boolean isError(int code){
        return code > 2000;
    }

    /**
     * 根据 code及使用者提供的字符串拼接对应提示
     * */
    public static String getErrorMsg(int code, String myMessage){
        return  TextUtils.isEmpty(myMessage) ? getErrorMsg(code) : getErrorMsg(code) + "_" + myMessage;
    }

    /**
     * 直接返回对应的错误信息
     * */
    public static String getErrorMsg(int code){
        Integer stringId = messages.get(code);
        if (stringId != null){
            return Utils.getContext().getString(stringId);
        }else {
            return "";
        }
    }
}
