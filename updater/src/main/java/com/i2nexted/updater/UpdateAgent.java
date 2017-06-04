package com.i2nexted.updater;

import android.content.Context;
import android.os.AsyncTask;

import com.i2nexted.updater.defalt.DefaultDialogDownloadListener;
import com.i2nexted.updater.defalt.DefaultDownloadListener;
import com.i2nexted.updater.defalt.DefaultFailureListener;
import com.i2nexted.updater.defalt.DefaultNotificationDownloadListener;
import com.i2nexted.updater.defalt.DefaultUpdateChecker;
import com.i2nexted.updater.defalt.DefaultUpdateDownloader;
import com.i2nexted.updater.defalt.DefaultUpdateParser;
import com.i2nexted.updater.defalt.DefaultUpdatePrompter;
import com.i2nexted.updater.interfaces.ICheckAgent;
import com.i2nexted.updater.interfaces.IDownloadAgent;
import com.i2nexted.updater.interfaces.IUpdateAgent;
import com.i2nexted.updater.interfaces.IUpdateChecker;
import com.i2nexted.updater.interfaces.IUpdateDownloader;
import com.i2nexted.updater.interfaces.IUpdateParser;
import com.i2nexted.updater.interfaces.IUpdatePrompter;
import com.i2nexted.updater.interfaces.OnDownloadListener;
import com.i2nexted.updater.interfaces.OnFailureListener;
import com.i2nexted.updater.util.ErrorMessageUtil;
import com.i2nexted.updater.util.UpdateUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/5/26.
 */

public class UpdateAgent implements ICheckAgent,IUpdateAgent,IDownloadAgent {
    private Context context;
    // 获取服务器端软件版本信息的地址
    private String mUrl;
    // 下载的安装包临时文件
    private File mTmpFile;
    // 安装包文件
    private File mApkFile;
    private boolean mIsManule;
    // 是否只在wifi环境下进行更新检查
    private boolean mIsWifiOnly;

    private UpdateError updateError;
    private UpdateInfo updateInfo;

    private IUpdateParser mParser;
    private IUpdateChecker mChecker;
    private IUpdateDownloader mDownloader;
    private IUpdatePrompter mPromtper;

    private OnFailureListener mOnFailureListener;
    private OnDownloadListener mOnDownloadListener;
    private OnDownloadListener mNotifyDownloadListener;

    public UpdateAgent(Context context, String mUrl, boolean mIsManule, boolean mIsWifiOnly, int notifyId) {
        this.context = context;
        this.mUrl = mUrl;
        this.mIsManule = mIsManule;
        this.mIsWifiOnly = mIsWifiOnly;
        mParser = new DefaultUpdateParser();
        mOnFailureListener = new DefaultFailureListener(context);
        mOnDownloadListener = new DefaultDialogDownloadListener(context);
        if (notifyId > 0){
            mNotifyDownloadListener = new DefaultNotificationDownloadListener(context, notifyId);
        }else {
            mNotifyDownloadListener = new DefaultDownloadListener();
        }
    }

    public void setParser(IUpdateParser parser) {
        mParser = parser;
    }

    public void setChecker(IUpdateChecker checker) {
        mChecker = checker;
    }

    public void setDownloader(IUpdateDownloader downloader) {
        mDownloader = downloader;
    }

    public void setPrompter(IUpdatePrompter prompter) {
        mPromtper = prompter;
    }

    public void setOnNotificationDownloadListener(OnDownloadListener listener) {
        mNotifyDownloadListener = listener;
    }

    public void setOnDownloadListener(OnDownloadListener listener) {
        mOnDownloadListener = listener;
    }

    public void setOnFailureListener(OnFailureListener listener) {
        mOnFailureListener = listener;
    }

    public void setInfo(UpdateInfo updateInfo){
        this.updateInfo = updateInfo;
    }

    @Override
    public void setInfo(String info) {
        try {

            updateInfo = mParser.parse(info);
        } catch (Exception e) {
            e.printStackTrace();
            setError(new UpdateError(ErrorMessageUtil.CHECK_PARSE));
        }
    }

    @Override
    public void setError(UpdateError error) {
        this.updateError = error;
    }

    @Override
    public UpdateInfo getInfo() {
        return updateInfo;
    }

    @Override
    public void update() {
        mApkFile = new File(context.getExternalCacheDir(), updateInfo.getMd5() + ".apk");
        if (UpdateUtil.verify(mApkFile, updateInfo.getMd5())) {
            doInstall();
        } else {
            doDownload();
        }
    }



    @Override
    public void ignore() {
        UpdateUtil.setIgnore(context, getInfo().getMd5());
    }

    @Override
    public void start() {
        if (updateInfo.isSilent()) {
            mNotifyDownloadListener.start();
        }else {
            mOnDownloadListener.start();
        }
    }

    @Override
    public void onProgress(int progress) {
        if (updateInfo.isSilent()){
            mNotifyDownloadListener.onProgress(progress);
        }else {
            mOnDownloadListener.onProgress(progress);
        }
    }

    @Override
    public void onFinish() {
        if (updateInfo.isSilent()){
            mNotifyDownloadListener.onFinish();
        }else {
            mOnDownloadListener.onFinish();
        }

        if (updateError != null){
            mOnFailureListener.onFailure(updateError);
        }else {
            mTmpFile.renameTo(mApkFile);
            if (updateInfo.isAutoInstall()){
                doInstall();
            }
        }
    }

    public void check(){
        UpdateUtil.log("check");
        if (mIsWifiOnly){
            if (UpdateUtil.checkWifi(context)){
                doCheck();
            }else {
                doFailure(new UpdateError(ErrorMessageUtil.CHECK_NO_WIFI));
            }
        }else {
            if (UpdateUtil.checkNetwork(context)){
                doCheck();
            }else {
                doFailure(new UpdateError(ErrorMessageUtil.CHECK_NO_NETWORK));
            }
        }
    }


    private void doCheck() {
        new AsyncTask<String, Void, Void>(){

            @Override
            protected Void doInBackground(String... params) {
                if (mChecker == null){
                    mChecker = new DefaultUpdateChecker();
                }
                mChecker.check(UpdateAgent.this, mUrl);
                return null;
            }

            @Override
            protected void onPreExecute() {
                doCheckFinish();
            }
        }.execute();
    }

    private void doCheckFinish(){
        UpdateError error = updateError;
        if (error != null){
            doFailure(updateError);
        }else {
           UpdateInfo info = getInfo();
            if (info == null){
                doFailure(new UpdateError(ErrorMessageUtil.CHECK_UNKNOWN));
            }else if (!info.isHasNew()){
                doFailure(new UpdateError(ErrorMessageUtil.UPDATE_NO_NEWER));
            }else if (UpdateUtil.isIgnore(context, info.getMd5())){
                doFailure(new UpdateError(ErrorMessageUtil.UPDATE_IGNORED));
            }else {
                UpdateUtil.ensureExternalCacheDir(context);
                UpdateUtil.setUpdate(context, info.getMd5());
                mTmpFile = new File(context.getExternalCacheDir(), info.getMd5());
                mApkFile = new File(context.getExternalCacheDir(), info.getMd5() + ".apk");
                if (UpdateUtil.verify(mApkFile, info.getMd5())){
                    doInstall();
                }else if (info.isSilent()){
                    doDownload();
                }else {
                    doPrompt();
                }
            }
        }
    }

    private void doPrompt() {
        if (mPromtper == null){
            mPromtper = new DefaultUpdatePrompter(context);
            mPromtper.prompt(this);
        }
    }

    private void doDownload() {
        if (mDownloader == null){
            mDownloader = new DefaultUpdateDownloader(context);
            mDownloader.download(this, updateInfo.getUrl(), mTmpFile);
        }
    }

    private void doInstall() {
        UpdateUtil.install(context, mApkFile, updateInfo.isForce());
    }

    private void doFailure(UpdateError updateError) {
        if (mIsManule || ErrorMessageUtil.isError(updateError.code)){
            mOnFailureListener.onFailure(updateError);
        }
    }



}
