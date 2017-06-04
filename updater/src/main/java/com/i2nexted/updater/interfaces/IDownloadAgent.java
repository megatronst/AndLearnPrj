package com.i2nexted.updater.interfaces;

import com.i2nexted.updater.UpdateError;
import com.i2nexted.updater.UpdateInfo;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IDownloadAgent extends OnDownloadListener {

    UpdateInfo getInfo();
    void setError(UpdateError updateError);
}
