package com.i2nexted.updater.interfaces;

import java.io.File;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IUpdateDownloader {
    void download(IDownloadAgent agent, String url, File temp);
}
