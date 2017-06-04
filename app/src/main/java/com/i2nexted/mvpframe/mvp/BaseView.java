package com.i2nexted.mvpframe.mvp;

/**
 * Created by Administrator on 2017/5/4.
 */

public interface BaseView<T> {
    /**
     * 设置界面的presenter
     * */
    void setPresenter(T presenter);

    /**
     * 根据网络请求结果返回的不同状态码来设置不同的结果页面
     * @param resultCode 有以下几种页面
     *                   1：正常的结果页面，显示网络请求得到的结果
     *                   2: 正常的结果界面，但是没有对应的数据
     *                   3：网络错误，诸如 超时 等等的错误
     *                   4: 请求成功，但是返回的结果错误
     *                   5：没有网络连接的错误
     *所有这些code都是在网络请求完成后根据网络请求的结果获取到的
     * */
    void setRequestRusultPage(int resultCode);
}
