package com.i2nexted.updater.interfaces;

import com.i2nexted.updater.UpdateInfo;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IUpdateParser {
    UpdateInfo parse(String json) throws Exception;
}
