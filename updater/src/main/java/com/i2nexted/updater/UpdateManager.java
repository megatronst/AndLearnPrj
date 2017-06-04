package com.i2nexted.updater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.i2nexted.updater.defalt.DefaultUpdateChecker;
import com.i2nexted.updater.interfaces.IUpdateChecker;
import com.i2nexted.updater.interfaces.IUpdateDownloader;
import com.i2nexted.updater.interfaces.IUpdateParser;
import com.i2nexted.updater.interfaces.IUpdatePrompter;
import com.i2nexted.updater.interfaces.OnDownloadListener;
import com.i2nexted.updater.interfaces.OnFailureListener;
import com.i2nexted.updater.util.UpdateUtil;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/5/31.
 */

public class UpdateManager {
    private static String sUrl;
    private static String sChannel;

    private static boolean isWifiOnly = true;

    public static void setIsWifiOnly(boolean wifeOnly){
        isWifiOnly = wifeOnly;
    }

    public static void setUrl(String url, String channel){
        sUrl = url;
        sChannel = channel;
    }

    public static void setDebuggable(boolean debuggable){
        UpdateUtil.setDebuggable(debuggable);
    }

    public static void install(Context context){
        UpdateUtil.install(context, true);
    }

    public static void check(Context context){
        create(context).check();
    }

    public static void checkMannul(Context context){
        create(context).setMannule(true).check();
    }

    public static Builder create(Context context){
        UpdateUtil.ensureExternalCacheDir(context);
        return new Builder(context).setWifiOnly(isWifiOnly);
    }

    /**
     * 版本更新检查器的构造者
     * */
    public static class Builder{
        private Context context;
        private boolean isWifiOnly;
        private boolean isMannule;
        private String url;
        private byte[] mPostData;
        private int mNotifyId = 0;

        private OnDownloadListener onDialogDownloadListener;
        private OnDownloadListener onNotifyDownloadListener;
        private OnFailureListener onFailureListener;
        private IUpdateChecker mChecker;
        private IUpdateParser mParser;
        private IUpdatePrompter mPrompter;
        private IUpdateDownloader mDownloader;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public Builder setPostData(@NonNull byte[] data){
            mPostData = data;
            return this;
        }

        public Builder setPostData(@NonNull String data){
            mPostData = data.getBytes(Charset.forName("UTF-8"));
            return this;
        }

        public Builder setNotifyId(int notifyId){
            mNotifyId = notifyId;
            return this;
        }

        public Builder setWifiOnly(boolean isWifiOnly){
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        public Builder setMannule(boolean isMannule){
            this.isMannule = isMannule;
            return this;
        }

        public Builder setParser(IUpdateParser parser){
            this.mParser = parser;
            return this;
        }

        public Builder setChecker(IUpdateChecker checker){
            this.mChecker = checker;
            return this;
        }

        public Builder setDownloader(IUpdateDownloader downloader){
            this.mDownloader = downloader;
            return this;
        }

        public Builder setPrompter(IUpdatePrompter prompter){
            this.mPrompter = prompter;
            return this;
        }

        public Builder setOnfailureListener(OnFailureListener failureListener){
            this.onFailureListener = failureListener;
            return this;
        }

        public Builder setNotifyDownloadListener(OnDownloadListener downloadListener){
            this.onNotifyDownloadListener = downloadListener;
            return this;
        }

        public Builder setDownloadListener(OnDownloadListener downloadListener){
            this.onDialogDownloadListener = downloadListener;
            return  this;
        }

        private static long mLastTime;

        public void check(){
            // 判断，如果在3秒钟以内已进行过更新检查，则本次检查跳过
            long now = System.currentTimeMillis();
            if (now - mLastTime < 3000){ return; }
            mLastTime = now;
            // 如果没有设置版本更新检查url则使用默认拼接的方式构造
            if (TextUtils.isEmpty(url)){
                url = UpdateUtil.toCheckUrl(context, sUrl, sChannel);
            }

            UpdateAgent agent = new UpdateAgent(context, url, isMannule, isWifiOnly, mNotifyId);
            if (onNotifyDownloadListener != null) {
                agent.setOnNotificationDownloadListener(onNotifyDownloadListener);
            }
            if (onDialogDownloadListener != null) {
                agent.setOnDownloadListener(onDialogDownloadListener);
            }
            if (onFailureListener != null) {
                agent.setOnFailureListener(onFailureListener);
            }
            if (mChecker != null) {
                agent.setChecker(mChecker);
            } else {
                agent.setChecker(new DefaultUpdateChecker(mPostData));
            }
            if (mParser != null) {
                agent.setParser(mParser);
            }
            if (mDownloader != null) {
                agent.setDownloader(mDownloader);
            }
            if (mPrompter != null) {
                agent.setPrompter(mPrompter);
            }
            agent.check();
        }
    }
}
