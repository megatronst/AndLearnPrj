package com.i2nexted.updater.defalt;

import android.content.Context;

import com.i2nexted.updater.interfaces.IDownloadAgent;
import com.i2nexted.updater.interfaces.IUpdateDownloader;
import com.i2nexted.updater.UpdateDownloader;

import java.io.File;

/**
 * Created by Administrator on 2017/5/27.
 */

public class DefaultUpdateDownloader implements IUpdateDownloader {
    final Context context;

    public DefaultUpdateDownloader(Context context) {
        this.context = context;
    }

    @Override
    public void download(IDownloadAgent agent, String url, File temp) {
        new UpdateDownloader(context, agent, temp, url).execute();
    }
}
