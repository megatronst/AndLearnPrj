package com.i2nexted.updater.interfaces;

/**
 * Created by Administrator on 2017/5/26.
 * 对下载过程的监听
 */

public interface OnDownloadListener {
    void start();
    void onProgress(int progress);
    void onFinish();
}
