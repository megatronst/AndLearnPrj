package com.i2nexted.mvpframe.api.response;

/**
 * Created by Administrator on 2017/5/22.
 */

public class HttpResult<T> extends BaseResponse{
    private T results;

    public T getResult() {
        return results;
    }
    public void setResult(T result) {
        this.results = result;

    }
}
