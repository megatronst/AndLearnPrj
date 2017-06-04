package com.i2nexted.mvpframe.mvp.baseact;


import com.i2nexted.mvpframe.mvp.BasePresenter;
import com.i2nexted.mvpframe.mvp.basefrag.BaseFragment;

/**
 * Created by Administrator on 2017/5/4.
 */

public abstract class BaseFragmentActivit<T extends BasePresenter> extends BaseActivity<T >{
    //布局中Fragment的ID
    protected abstract int getFragmentContentId();
    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 调用BaseFragment 的setFunctions 来设置通信用的方法
     * */
    public abstract void setFunctionForFrag(String tag);
}
