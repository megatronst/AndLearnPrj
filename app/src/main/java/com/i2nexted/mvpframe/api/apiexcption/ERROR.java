package com.i2nexted.mvpframe.api.apiexcption;

/**
 * Created by Administrator on 2017/5/22.
 */

/**
 * 约定异常
 */

public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;

    /**
     * 没有网络连接
     * */
    public static final int NETWORK_NOT_AVILIABLE = 1004;
}
