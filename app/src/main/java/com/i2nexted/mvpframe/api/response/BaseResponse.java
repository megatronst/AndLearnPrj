package com.i2nexted.mvpframe.api.response;

/**
 * Created by Administrator on 2017/5/22.
 */

public class BaseResponse {
    private int code;
    private boolean isSuccess;
    private String msg;
    private boolean error = true;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return !error;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
