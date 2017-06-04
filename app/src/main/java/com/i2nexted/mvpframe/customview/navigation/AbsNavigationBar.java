package com.i2nexted.mvpframe.customview.navigation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/18.
 */

public abstract class AbsNavigationBar <P extends AbsNavigationBar.Builder.NavigationParams>   implements INavigation{
    private P param;
    private View view;

    public AbsNavigationBar(P param) {
        this.param = param;
        createAndBind();
    }

    /**
     * 获取字符串
     * @param id 字符串资源id
     * */
    protected String getString(int id){
        return this.param.context.getResources().getString(id);
    }

    /**
     * 获取颜色
     * @param id 颜色资源id
     * */
    protected int getColor(int id){
        return ContextCompat.getColor(this.param.context, id);
    }

    /**
     * 获取参数
     * */
    protected P getParam(){
        return this.param;
    }

    /**
     * 获取对应的控件
     * */
    protected <T extends View> T findViewById(int id){
        return (T)view.findViewById(id);
    }

    /**
     * 设置文本
     * @param id  控件的id
     * @param text 文本
     * */
    protected void setText(int id, CharSequence text){
        TextView textView = findViewById(id);
        if (textView != null && !TextUtils.isEmpty(text)){
            textView.setText(text);
        }
    }

    /**
     * 设置点击事件
     * @param id 控件id
     * @param listener 时间监听
     * */
    protected void setOnClickListener(int id, View.OnClickListener listener){
        View view = findViewById(id);
        if (view != null && listener != null){
            view.setOnClickListener(listener);
        }
    }

    /**
     * 设置图片资源
     * @param id 控件id
     * @param rscId 图片资源id
     * */
    protected void setImageResource(int id, int rscId){
        ImageView imageView = findViewById(id);
        if (imageView != null){
            imageView.setImageResource(rscId);
        }
    }

    /**
     * 设置导航栏的背景颜色
     * */
    protected void setBackgroundColor(int id, int color){
        View view = findViewById(id);
        if (view != null){
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置控件的隐藏和显示
     * @param id 控件的id
     * @param show true 为显示 false为隐藏
     * */
    protected void showView(int id, boolean show){
        View view = findViewById(id);
        if (view != null){
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 创建和绑定布局
     * */
    protected void createAndBind(){
        if (param == null) return;
        view = LayoutInflater.from(param.context).inflate(bingLayoutId(), param.parent, false);
        param.parent.addView(view, 0);
        applyView();
    }
    public abstract static class Builder{
        /**
         * 构建导航栏
         * */
        public abstract AbsNavigationBar create();

        /**
         * 默认配置参数
         * */
        public abstract static class NavigationParams{
            public Context context;
            public ViewGroup parent;

            public NavigationParams(Context context, ViewGroup parent) {
                this.context = context;
                this.parent = parent;
            }
        }

    }
}
