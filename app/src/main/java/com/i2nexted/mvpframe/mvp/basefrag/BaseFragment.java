package com.i2nexted.mvpframe.mvp.basefrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i2nexted.mvpframe.mvp.BasePresenter;
import com.i2nexted.mvpframe.mvp.baseact.BaseActivity;
import com.i2nexted.mvpframe.mvp.baseact.BaseFragmentActivit;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/4.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseFragmentInterface{
    protected P mPresenter;
    private boolean useEventBus;
    protected Functions functions; // 用于与activity通信的方法集合
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 调用activity中的方法为Fragment设置通信用的方法
        if (context instanceof BaseActivity){
            ((BaseFragmentActivit)context).setFunctionForFrag(getTag());
        }
        initParam();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useEventBus = setUseEventBus();
        if (useEventBus){
            EventBus.getDefault().register(this);
        }
        // 初始化Presenter
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return getView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
//        unSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus){
            EventBus.getDefault().unregister(this);
        }
        unSubscribe();
    }


    /**
     * 解除presenter的监听
     * */
    private void unSubscribe(){
        if (mPresenter != null){
            mPresenter.unSubscribe();
        }
    }

    /**
     * 设置是否使用EventBus
     * */
    protected boolean setUseEventBus(){
        return false;
    }

    /**
     * 初始化presenter
     * */
    protected abstract P onCreatePresenter();

    /**
     * 在activity中通过此方法来设置用于通信的方法集合
     * */
    public void setFunctions(Functions functions){
        this.functions = functions;
    }
}
