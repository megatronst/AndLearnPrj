package com.i2nexted.mvpframe.adapter.rvadapter.itemdecor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.i2nexted.mvpframe.R;

/**
 * Created by Administrator on 2017/5/23.
 * 水平列表或者纵向列表的分割符
 */

public class DividerListDecor extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    private int orientation;
    // 分割线的高度或宽度
    private int dimmension;
    /**
     * @param drawableId 分割线的样式
     * @param dimmension 分割线的宽度或者高度 以px为单位
     * */
    public DividerListDecor(Context context, int orientation, int drawableId, int dimmension){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = ContextCompat.getDrawable(context,R.drawable.list_divider_margin_14dp);
        this.dimmension = dimmension;
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * 设置方向
     * */
    private void setOrientation(int orientation){
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw  new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        if (HORIZONTAL_LIST == orientation){
            drawHorizontal(c, parent);
        }else {
            drawVertical(c, parent);
        }
    }

    /**
     * 绘制纵向列表的分割线，此分割线为水平分割线
     * */
    private void drawVertical(Canvas c, RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + dimmension;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    /**
     * 绘制横向列表的分割线，此分割线为垂直分割线
     * */
    private void drawHorizontal(Canvas c, RecyclerView parent){
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++){
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + dimmension;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (VERTICAL_LIST == orientation){
            outRect.set(0, 0, 0, dimmension);

        }else {
            outRect.set(0, 0, dimmension, 0);
        }
    }
}
