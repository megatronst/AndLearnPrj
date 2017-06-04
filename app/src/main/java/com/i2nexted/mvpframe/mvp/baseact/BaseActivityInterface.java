package com.i2nexted.mvpframe.mvp.baseact;

import com.i2nexted.mvpframe.mvp.BaseFuncInterface;

/**
 * Created by Administrator on 2017/5/4.
 */

public interface BaseActivityInterface  extends BaseFuncInterface {
    /**
     * 设置界面布局
     * */
    void setContentView();

    /**
     * 设置标题
     * */
    void initTitle();
}
