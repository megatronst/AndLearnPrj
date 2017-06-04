package com.i2nexted.mvpframe.customview.navigation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.i2nexted.mvpframe.R;


/**
 * Created by Administrator on 2017/5/19.
 * 默认的导航条 使用的场景是 文字标题 左右为图片或者文字控件 且左右都可点击
 */

public class DefaultNavigationBar  extends AbsNavigationBar<DefaultNavigationBar.DefaultNavigationParam>{

    public DefaultNavigationBar(DefaultNavigationParam param) {
        super(param);
    }

    @Override
    public int bingLayoutId() {
        // TODO 这里根据实际的需要写好导航条的布局过后将布局放到这里即可
        return R.layout.layout_default_navigationbar;
    }

    @Override
    public void applyView() {
        // TODO 这里根据导航条的实际布局和控件id来对每一个控件设置对应的图片、文字、时间监听方法
        // 设置分割线相关属性
        showView(R.id.id_view_navidivider, getParam().showNaviDivider);
        if (getParam().showNaviDivider){
            setBackgroundColor(R.id.id_view_navidivider, getParam().dividerColor);
        }
        // 设置导航栏左边的显示
        switch (getParam().leftType){
            case DefaultNavigationParam.TYPE_IMAGE:
                showView(R.id.id_tv_lefttext, false);
                showView(R.id.id_img_leftimg, true);
                setImageResource(R.id.id_img_leftimg, getParam().leftIcon);
                break;
            case DefaultNavigationParam.TYPE_TEXT:
                showView(R.id.id_tv_lefttext, true);
                showView(R.id.id_img_leftimg, false);
                setText(R.id.id_tv_lefttext, getParam().leftText);
                break;
            case DefaultNavigationParam.TYPE_NONE:
                showView(R.id.id_tv_lefttext, false);
                showView(R.id.id_img_leftimg, false);
                break;
        }
        // 设置导航栏右边的显示
        switch (getParam().rightType){
            case DefaultNavigationParam.TYPE_IMAGE:
                showView(R.id.id_tv_righttext, false);
                showView(R.id.id_img_rightimg, true);
                setImageResource(R.id.id_img_rightimg, getParam().rightIcon);
                break;
            case DefaultNavigationParam.TYPE_TEXT:
                showView(R.id.id_tv_righttext, true);
                showView(R.id.id_img_rightimg, false);
                setText(R.id.id_tv_righttext, getParam().rightText);
                break;
            case DefaultNavigationParam.TYPE_NONE:
                showView(R.id.id_tv_righttext, false);
                showView(R.id.id_img_rightimg, false);
                break;
        }
        // 设置标题
        setText(R.id.id_tv_title, getParam().title);
        // 设置背景
        setBackgroundColor(R.id.id_ll_navigation, getParam().bgColor);
        // 设置事件监听
        setOnClickListener(R.id.id_ll_leftlayout, getParam().leftClickListener);
        setOnClickListener(R.id.id_ll_rightlayout, getParam().rightClickListener);
    }

    /**
     * 使用建造者模式构建导航条
     * */
    public static class Builder extends AbsNavigationBar.Builder{
        private DefaultNavigationParam param;

        public Builder(Context context) {
            this.param = new DefaultNavigationParam(context);
        }

        public Builder setTitle(String title){
                param.title = title;
            return this;
        }

        public Builder setRightText(String rightText){
            param.rightText = rightText;
            return this;
        }

        public Builder setLeftText(String leftText){
            param.leftText = leftText;
            return this;
        }

        public Builder setRightIcon(int rightIcon){
            param.rightIcon = rightIcon;
            return this;
        }

        public Builder setLeftIcon(int leftIcon){
            param.leftIcon = leftIcon;
            return this;
        }

        public Builder setBackGroundColor(int color){
            param.bgColor = color;
            return this;
        }

        public Builder setRightClickListener(View.OnClickListener rightClickListener){
            param.rightClickListener = rightClickListener;
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener leftClickListener){
            param.leftClickListener = leftClickListener;
            return this;
        }

        public Builder setLeftShowType(int type){
            param.leftType = type;
            return this;
        }

        public Builder setRightShowType(int type){
            param.rightType = type;
            return this;
        }

        public Builder showNaviDivider(boolean show){
            param.showNaviDivider = show;
            return this;
        }

        public Builder setNaviDividerColoer(int color){
            param.dividerColor = color;
            return this;
        }

        @Override
        public DefaultNavigationBar create() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(param);
            return navigationBar;
        }
    }

    /**
     * 默认的配置参数
     * */
    public static  class DefaultNavigationParam extends AbsNavigationBar.Builder.NavigationParams{
        // 导航栏左边和右边的显示类型分为三种，分别如下
        // 显示图片
        public static final int TYPE_IMAGE = 0;
        // 显示文字
        public static final int TYPE_TEXT = 1;
        // 不予显示
        public static final int TYPE_NONE = 2;

        // 导航栏左边显示的控件类型
        public int leftType = TYPE_IMAGE;
        // 导航栏左边显示的控件类型
        public int rightType = TYPE_IMAGE;
        // 标题
        public String title = " ";
        // 左边图片资源
        public int leftIcon;
        // 右边图片资源
        public int rightIcon;
        // 左边文字
        public String leftText= " ";
        // 右边文字
        public String rightText= " ";
        // 背景颜色
        public int bgColor;
        // 左边的点击事件监听
        public View.OnClickListener leftClickListener;
        // 右边的点击事件监听
        public View.OnClickListener rightClickListener;
        // 是否显示分割线
        public  boolean showNaviDivider = true;
        // 分割线颜色
        public int dividerColor = Color.parseColor("#000000");

        public DefaultNavigationParam(Context context) {
            super(context, (ViewGroup)((((Activity)context).findViewById(android.R.id.content))).getParent());
        }
    }
}
