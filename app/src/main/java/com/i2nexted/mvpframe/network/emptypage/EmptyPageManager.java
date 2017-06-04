package com.i2nexted.mvpframe.network.emptypage;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.i2nexted.mvpframe.R;

/**
 * Created by Administrator on 2017/5/24.
 * 使用建造者模式生成对应的空白页,默认提供一下几种空白页
 * *                 1：没有网络连接的错误
 *                   2: 正常的结果界面，但是没有对应的数据
 *                   3：网络错误，诸如 超时 等等的错误
 *                   4: 请求成功，但是返回的结果错误
 * 获取到默认的布局过后需要单独设置事件监听
 */

public class EmptyPageManager {
    // 几种默认的布局
    public static int EMPTYPAGE_TYPE_NETWORK_NOTAVILIABLE = 0;
    public static int EMPTYPAGE_TYPE_HTTP_ERROR = 1;
    public static int EMPTYPAGE_TYPE_NODATA = 2;
    public static int EMPTYPAGE_TYPE_DATA_ERROR = 3;
    private static int[] drawableIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private static int[] texts = new int[]{R.string.emptypage_network_notaviliable, R.string.emptypage_http_error, R.string.emptypage_nodata, R.string.emptypage_data_error};
    private SparseArray<String> text;
    public static View getEmptypage(Context context, ViewGroup parent, int type, View.OnClickListener listener){
        return new Builder(context)
                .setParent(parent)
                .setOnClickListener(listener)
                .setText(context.getString(texts[type]))
                .setImg(drawableIds[type])
                .generateEmptyPage();
    }



    public static class Builder{
        private EmptyPageViewHelper emptyPageViewHelper;
        private EmptyPagePram param;
        private Context context;
        public Builder(Context context){
            param = new EmptyPagePram();
            this.context = context;
        }

        /**
         * 设置空白页的主布局
         * */
        public Builder setContentView(int layoutId){
            param.layoutId = layoutId;
            return this;
        }

        /**
         * 设置空白页的提示图片
         * */
        public Builder setImg(int viewId,int drawableId){
            param.drawableId = drawableId;
            param.imgViewId = viewId;
            return this;
        }
        /**
         * 设置空白页的提示图片
         * */
        public Builder setImg(int drawableId){
            param.drawableId = drawableId;
            return this;
        }

        /**
         * 设置空白页的文字提示
         * */
        public Builder setText(int viewId,String text){
            param.text = text;
            param.tvId = viewId;
            return this;
        }
        /**
         * 设置空白页的文字提示
         * */
        public Builder setText(String text){
            param.text = text;
            return this;
        }

        /**
         * 设置空白页的文字提示
         * */
        public Builder setParent(ViewGroup parent){
            param.parent = parent;
            return this;
        }

        /**
         * 设置点击事件监听
         * */
        public Builder setOnClickListener(View.OnClickListener listener){
            param.listener = listener;
            return this;
        }

        /**
         * 使用设置的参数，来设置对应的控件
         * */
        private View applyView(){
            emptyPageViewHelper = new EmptyPageViewHelper(context, param.parent, param.layoutId);
            emptyPageViewHelper.setText(param.tvId, param.text);
            emptyPageViewHelper.setImage(param.imgViewId, param.drawableId);
            emptyPageViewHelper.setOnClickListener(param.listener);
            return emptyPageViewHelper.getContentView();
        }

        /**
         * 生成空白页
         * */
        public View generateEmptyPage(){
            return applyView();
        }


        public static class EmptyPagePram{
            // 添加默认的参数
            public int tvId = R.id.id_tv_error;
            public int imgViewId = R.id.id_img_error;
            public int layoutId = R.layout.layout_emptypage;
            public int drawableId;
            public String text = "";
            public ViewGroup parent;
            public View.OnClickListener listener;
        }
    }
}
