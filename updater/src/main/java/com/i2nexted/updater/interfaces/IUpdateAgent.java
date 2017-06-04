package com.i2nexted.updater.interfaces;

import com.i2nexted.updater.UpdateInfo;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IUpdateAgent {
    UpdateInfo getInfo();
    void update();
    void ignore();
}
