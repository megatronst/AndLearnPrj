package com.i2nexted.updater.interfaces;

import com.i2nexted.updater.UpdateError;

/**
 * Created by Administrator on 2017/5/26.
 * 软件更新过程的失败监听
 */

public interface OnFailureListener {
    void onFailure(UpdateError updateError);
}
