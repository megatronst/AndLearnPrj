package com.i2nexted.updater.interfaces;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IUpdateChecker {
    void check(ICheckAgent agent, String url);
}
