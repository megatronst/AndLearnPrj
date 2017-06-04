package com.i2nexted.mvpframe.api.apiexcption;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;
}
