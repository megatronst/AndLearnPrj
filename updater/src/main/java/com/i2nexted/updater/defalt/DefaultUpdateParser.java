package com.i2nexted.updater.defalt;

import com.i2nexted.updater.interfaces.IUpdateParser;
import com.i2nexted.updater.UpdateInfo;

/**
 * Created by Administrator on 2017/5/26.
 */

public class DefaultUpdateParser implements IUpdateParser {
    @Override
    public UpdateInfo parse(String json) throws Exception {
        return UpdateInfo.parse(json);
    }
}
