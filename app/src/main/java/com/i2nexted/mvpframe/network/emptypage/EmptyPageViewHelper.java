package com.i2nexted.mvpframe.network.emptypage;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/24.
 */

public class EmptyPageViewHelper {
    private View mContentView;
    // 使用弱引用防止内存泄漏
    private SparseArray<WeakReference<View>> mViews;

    public EmptyPageViewHelper(Context context, ViewGroup parent, int layoutId){
        mContentView = LayoutInflater.from(context).inflate(layoutId,parent, false);
//        mContentView =((BaseActivity)context).getLayoutInflater().inflate(layoutId,parent, false);
        mViews = new SparseArray<>();
    }

    /**
     * 根据id获取对应的控件
     * */
    public <T extends View> T getView(int id){
        View view = null;
        WeakReference<View> viewWeakReference = mViews.get(id);
        if (viewWeakReference !=null && viewWeakReference.get() !=null){
            view = viewWeakReference.get();
        }
        if (view == null){
            view = mContentView.findViewById(id);
            viewWeakReference = new WeakReference<>(view);
            mViews.put(id, viewWeakReference);
        }
        return (T)view;
    }

    /**
     * 获取总体布局
     * */
    public View getContentView(){
        return mContentView;
    }

    /**
     * 根据控件id来设置文字
     * */
    public void setText(int id, String text){
        TextView textView = getView(id);
        if (textView != null && !TextUtils.isEmpty(text)){
            textView.setText(text);
        }
    }

    /**
     * 根据id来设置一般的图片（ImageView）
     **/
    public void setImage(int id, int drawableId){
        ImageView imageView = getView(id);
        if (imageView != null){
            imageView.setImageResource(drawableId);
        }
    }

    /**
     * 根据id来设置SimpleDraweeview 图片
     * */
    public void setFrescoImage(int id, int drawableId){
        SimpleDraweeView simpleDraweeView = getView(id);
        if (simpleDraweeView != null){
            simpleDraweeView.setImageResource(drawableId);
        }
    }

    /**
     * 设置点击事件监听，点击整个空白界面的时候出发一些操作
     * */
    public void setOnClickListener(View.OnClickListener listener){
        mContentView.setOnClickListener(listener);
    }
}
