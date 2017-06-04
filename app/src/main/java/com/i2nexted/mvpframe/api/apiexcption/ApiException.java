package com.i2nexted.mvpframe.api.apiexcption;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ApiException extends Exception{
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }
}
