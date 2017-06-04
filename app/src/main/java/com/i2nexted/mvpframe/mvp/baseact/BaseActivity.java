package com.i2nexted.mvpframe.mvp.baseact;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.i2nexted.mvpframe.customview.navigation.AbsNavigationBar;
import com.i2nexted.mvpframe.customview.navigation.DefaultNavigationBar;
import com.i2nexted.mvpframe.mvp.BasePresenter;
import com.i2nexted.mvpframe.util.KeyboardUtil;
import com.i2nexted.mvpframe.util.PhoneUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseActivityInterface {
    // mpv中的p
    private P mPresenter;
    // 设置是否添加EventBus监听
    private boolean useEventBus = false;
    // 设置是否使用点击空白处收起键盘功能
    private boolean keyboardAutoClose = false;
    // 导航栏
    private AbsNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化Presenter
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }

        // 设置界面布局
        setContentView();
        // 统一设置导航栏
        initTitle();
        // 去掉界面的默认背景，必须要在setcontentview之后执行
        getWindow().setBackgroundDrawable(null);
        // view注入
        ButterKnife.bind(this);
        // 初始化变量
        initParam();
        // 初始化view
        initView();
        // 初始化控件时间监听
        initListener();
        // 开始加载数据
        initData();
        // 设置是否使用EventBus
        useEventBus = useEventBus();
        if (useEventBus) {
            EventBus.getDefault().register(this);
        }
        // 设置是否使用点击空白处自动收起软键盘的功能
        keyboardAutoClose = useKeyBoardAutoCloseMode();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
        if (useEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unSubscribe();
    }

    /**
     * 事件分发处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 判断用户是否是连续点击如果是则返回true不执行相关操作，否则的话执行相关操作
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (PhoneUtil.isFastTab()) {
                return true;
            }
        }
        // 执行隐藏键盘的操作，如果确实是在键盘打开的情况下成功收起了键盘则返回true时间不再继续传递，否则返回false事件继续传递
        if (keyboardAutoClose) {
            if (KeyboardUtil.hideKeyboard(ev, this)) {
                return true;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 解除presenter的监听
     */
    private void unSubscribe() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    /**
     * 设置是否使用EventBus  默认不需要
     * 如果需要时用EventBus 则重写此方法返回true即可
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 设置是否使用点击空白处自动收起软键盘的功能  默认不需要
     * 如果需要使用点击空白处收起键盘的功能则在子类中重写此方法返回true即可
     */
    protected boolean useKeyBoardAutoCloseMode() {
        return false;
    }

    /**
     * 初始化presenter
     */
    protected abstract P onCreatePresenter();

    /**
     * 初始化导航栏，如果继承自本baseactivity 的其他activity 需要修改导航栏的样式则重写此方法即可
     */
    @Override
    public void initTitle() {
        View.OnClickListener leftClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        View.OnClickListener rightClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, "right clicked", Toast.LENGTH_SHORT).show();
            }
        };
        navigationBar = new DefaultNavigationBar.Builder(this)
                .setLeftClickListener(leftClickListener)
                .setRightClickListener(rightClickListener)
                .setTitle("title")
                .setRightShowType(DefaultNavigationBar.DefaultNavigationParam.TYPE_TEXT)
                .setLeftShowType(DefaultNavigationBar.DefaultNavigationParam.TYPE_TEXT)
                .setLeftText("left")
                .setRightText("right")
                .setBackGroundColor(Color.parseColor("#ff9c00")).create();
    }
}
