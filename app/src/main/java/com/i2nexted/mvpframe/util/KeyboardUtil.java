package com.i2nexted.mvpframe.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/5/4.
 */

public class KeyboardUtil {

    /**
     * 判断软键盘是否显示
     * */
    private static boolean isSoftKeyboardShow(Context context){
        //获取当前屏幕内容的高度
        int screenHeight = ((Activity)context).getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom > ScreenUtil.getBottomStatusHeight(context);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *这里是直接根据view的上边界和下边界来判断的
     * @param v
     * @param event
     * @return
     */
    private static boolean shouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            if (event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private static void hideKeyboard(IBinder token, Context context) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 共外界调用的软键盘收起方法
     * */
    public static boolean hideKeyboard(MotionEvent event, Context context){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            View v = ((Activity)context).getCurrentFocus();
            if (isSoftKeyboardShow(context) && shouldHideInput(v, event)){
                IBinder token = v.getWindowToken();
                hideKeyboard(token, context);
                return true;
            }
        }
        return false;

    }
}
