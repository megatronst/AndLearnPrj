package com.i2nexted.mvpframe.customview.dialog.basedialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.i2nexted.mvpframe.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class BaseDialog {

    private Dialog mDialog;
    protected Window mDialogWindow;
    // 对话框view辅助工具
    private DialogViewHolder mDialogViewHolder;
    // 对话框的布局
    private View rootView;
    private Context context;
    public BaseDialog(Context context, int layoutId){
        this.context = context;
        mDialogViewHolder = new DialogViewHolder(context, layoutId);
        rootView = mDialogViewHolder.getConvertView();
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(rootView);
        mDialogWindow = mDialog.getWindow();
        // 通过此方法实现具体的对话框相关布局和控件的初始化
        convertView();
    }

    public abstract void convertView();

    /**
     * 显示dialog
     * */
    public <T extends BaseDialog> T showDialog(){
        if (mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
        return (T)this;
    }

    /**
     *设置对话框弹出时原界面的亮度
     * @param light 0.0-1.0   数值越小越亮，取0.0 时小心闪瞎双眼
     * */
    public <T extends BaseDialog> T  setBackgroundLight(float light){
        float backLight = 0.5f;
        if (light >=0 && light <= 1){
            backLight = light;
        }
        WindowManager.LayoutParams windowLayoutParam = mDialogWindow.getAttributes();
        windowLayoutParam.dimAmount = backLight;
        mDialogWindow.setAttributes(windowLayoutParam);
        return (T)this;
    }

    /**
     *设置对话框的显示和消失的动画风格
     * */
    public <T extends BaseDialog> T  setTransitionStyle(AnimStyle style){
        switch (style){
            case BOTTOM:
                mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
                mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                break;
            case BOTTOM_TO_MIDDLE:
                mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
                break;
            case LEFT_TO_MIDDLE:
                mDialogWindow.setWindowAnimations(R.style.window_left_in_left_out);
                mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
                break;
            case RIGHT_TO_MIDDLE:
                mDialogWindow.setWindowAnimations(R.style.window_right_in_right_out);
                mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                mDialogWindow.setGravity(Gravity.RIGHT);
                break;
            case TOP:
                mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
                mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
                break;
            case TOP_TO_MIDDLE:
                mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
                mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                break;
            case SCALE:
                mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
                break;
        }
        return (T)this;
    }

    /**
     * 按照自定义方式弹出对话框
     * @param style 自己实现的动画
     * */
    public <T extends BaseDialog> T  showDialog(int style){
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return (T)this;
    }

    /**
     * 带默认缩放动画的方式显示对话框
     * */
    public <T extends BaseDialog> T  showDialog(boolean withAnim){
        mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
        mDialog.show();
        return (T)this;
    }

    /**
     * 设置对话框的尺寸大小
     * */
    public <T extends BaseDialog> T  setDialogSize(SizeStyle sizeStyle){
        WindowManager.LayoutParams layoutParams = mDialogWindow.getAttributes();
        switch (sizeStyle){
            case FULL_HEIGHT:
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case FULL_SCREEN:
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case FULL_WIDTH:
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case DEFAULT:
                break;
        }
        mDialog.onWindowAttributesChanged(layoutParams);
        return (T)this;
    }

    /**
     * 自定义对话框的宽高
     * */
    public <T extends BaseDialog> T  setDialogSize(int width, int height){
        WindowManager.LayoutParams layoutParams = mDialogWindow.getAttributes();
        layoutParams.height = height;
        layoutParams.width = width;
        mDialog.onWindowAttributesChanged(layoutParams);
        return (T)this;
    }

    /**
     * 关闭对话框
     * */
    public void dismiss(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    /**
     * 取消对话框
     * */
    public void cancel(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.cancel();
        }
    }

    /**
     *设置关闭对话框监听
     * */
    public <T extends BaseDialog> T  setDismissListener(DialogInterface.OnDismissListener dismissListener){
        if (mDialog != null){
            mDialog.setOnDismissListener(dismissListener);
        }
        return (T)this;
    }

    /**
     * 设置取消对话框监听
     * */
    public <T extends BaseDialog> T  setCancelListener(DialogInterface.OnCancelListener cancelListener){
        if (mDialog != null){
            mDialog.setOnCancelListener(cancelListener);
        }
        return (T)this;
    }

    /**
     * 设置是否可取消
     * */
    public <T extends BaseDialog> T  setCancelble(boolean isCancelble){
        if (mDialog != null){
            mDialog.setCancelable(isCancelble);
        }
        return (T)this;
    }

    /**
     * 设置是否可以通过点击对话框外部取消对话框
     * */
    public <T extends BaseDialog> T  setCancelbleOnTouchOutside(boolean isCancelbleOnTouochOutside){
        if (mDialog != null){
            mDialog.setCanceledOnTouchOutside(isCancelbleOnTouochOutside);
        }
        return (T)this;
    }

    public DialogViewHolder getDialogViewHolder(){
        return mDialogViewHolder;
    }

    protected Context getContext(){
        return context;
    }

    /**
     * 设置是否可以在application中显示,不掉用此方法的话默认是不能在application中显示dialog的
     * */
    public <T extends BaseDialog> T showInApplication(){
        mDialogWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return (T)this;
    }
}
