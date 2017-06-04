package com.i2nexted.mvpframe.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ItemKeyword implements Parcelable {
    private String index;
    private String title;
    private String voiceId;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.index);
        dest.writeString(this.title);
        dest.writeString(this.voiceId);
    }

    public ItemKeyword() {
    }

    protected ItemKeyword(Parcel in) {
        this.index = in.readString();
        this.title = in.readString();
        this.voiceId = in.readString();
    }

    public static final Creator<ItemKeyword> CREATOR = new Creator<ItemKeyword>() {
        @Override
        public ItemKeyword createFromParcel(Parcel source) {
            return new ItemKeyword(source);
        }

        @Override
        public ItemKeyword[] newArray(int size) {
            return new ItemKeyword[size];
        }
    };
}
