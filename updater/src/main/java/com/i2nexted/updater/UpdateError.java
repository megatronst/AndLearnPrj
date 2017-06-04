package com.i2nexted.updater;

import com.i2nexted.updater.util.ErrorMessageUtil;

/**
 * Created by Administrator on 2017/5/26.
 */

public class UpdateError extends Throwable{
    public final int code;

    public UpdateError(int code){
        this(code, null);
    }

    public UpdateError(int code, String message){
        super(ErrorMessageUtil.getErrorMsg(code,message));
        this.code = code;
    }

    @Override
    public String toString() {
        return "UpdateError{" +
                "code=" + code + "message= " + getMessage()+
                '}';
    }

}
