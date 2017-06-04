package com.i2nexted.mvpframe.mvp;

/**
 * Created by Administrator on 2017/5/4.
 */

public interface BaseFuncInterface {
    /*初始化一些变量*/
    void initParam();

    /* 初始化UI控件方法 */
    void initView();

    /* 初始化事件监听方法 */
    void initListener();

    /* 初始化数据方法 */
    void initData();



    /* 初始化界面加载方法 */
    void initLoadData();
}
