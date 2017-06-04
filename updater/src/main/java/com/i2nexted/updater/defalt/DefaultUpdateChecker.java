package com.i2nexted.updater.defalt;

import com.i2nexted.updater.util.ErrorMessageUtil;
import com.i2nexted.updater.interfaces.ICheckAgent;
import com.i2nexted.updater.interfaces.IUpdateChecker;
import com.i2nexted.updater.UpdateError;
import com.i2nexted.updater.util.UpdateUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/27.
 */

public class DefaultUpdateChecker implements IUpdateChecker {
    final byte[] mPostData;

    public DefaultUpdateChecker() {
        mPostData = null;
    }
    public DefaultUpdateChecker(byte[] data) {
        mPostData = data;
    }

    @Override
    public void check(ICheckAgent agent, String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Accept", "application/json");

            if (mPostData == null) {
                connection.setRequestMethod("GET");
                connection.connect();
            } else {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString(mPostData.length));
                connection.getOutputStream().write(mPostData);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                agent.setInfo(UpdateUtil.readString(connection.getInputStream()));
            } else {
                agent.setError(new UpdateError(ErrorMessageUtil.CHECK_HTTP_STATUS, "" + connection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            agent.setError(new UpdateError(ErrorMessageUtil.CHECK_NETWORK_IO));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
