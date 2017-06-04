package com.i2nexted.mvpframe.mvp.basefrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i2nexted.mvpframe.mvp.BaseFuncInterface;

/**
 * Created by Administrator on 2017/5/4.
 */

public interface BaseFragmentInterface extends BaseFuncInterface {
    /**
     * 初始化布局并且返回布局view
     * */
    View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
