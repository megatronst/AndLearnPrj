package com.i2nexted.updater;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/26.
 */

public class UpdateInfo {
    // 是否有新版本
    private boolean hasNew;
    // 静默下载，不提示用户直接下载
    private boolean isSilent;
    // 是否强制安装
    private boolean isForce;
    // 下载完成后是否自动安装
    private boolean isAutoInstall;
    // 是否可忽略
    private boolean isIgnorable;
    // 一天之内的提示更新的最大次数 大于1时为对应的次数，小于1时不予限制次数
    private int maxTimes = 0;
    // 版本号
    private int versionCode;
    // 版本名称
    private String versionName;
    // 更新的具体内容提示
    private String updateContent;
    // apk下载地址
    private String url;
    // apk文件的md5码，用于校验下载的文件是否有损坏
    private String md5;
    // 文件大小
    private long size;

    public boolean isHasNew() {
        return hasNew;
    }

    public void setHasNew(boolean hasNew) {
        this.hasNew = hasNew;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isAutoInstall() {
        return isAutoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public void setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
    }

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public static UpdateInfo parse(String s) throws JSONException{
        JSONObject jsonObject = new JSONObject(s);
        return parse(jsonObject.has("data") ? jsonObject.getJSONObject("data") : jsonObject);
    }

    public static UpdateInfo parse(JSONObject jsonObject){
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.hasNew = jsonObject.optBoolean("hasNew", false);
        // 如果没有可用更新则直接返回
        if (!updateInfo.hasNew){
            return updateInfo;
        }
        updateInfo.isSilent = jsonObject.optBoolean("isSilient", false);
        updateInfo.isForce = jsonObject.optBoolean("isForce", false);
        updateInfo.isAutoInstall = jsonObject.optBoolean("isAutoInstall", false);
        updateInfo.isIgnorable = jsonObject.optBoolean("isIgnorable", true);
        updateInfo.versionCode = jsonObject.optInt("versionCode", 0);
        updateInfo.versionName = jsonObject.optString("versionName");
        updateInfo.updateContent = jsonObject.optString("updateContent");
        updateInfo.url = jsonObject.optString("url");
        updateInfo.md5 = jsonObject.optString("md5");
        updateInfo.size = jsonObject.optLong("size", 0);
        return updateInfo;
    }
}
