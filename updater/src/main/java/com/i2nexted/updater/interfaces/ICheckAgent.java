package com.i2nexted.updater.interfaces;

import com.i2nexted.updater.UpdateError;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface ICheckAgent {
    void setInfo(String info);
    void setError(UpdateError error);
}
