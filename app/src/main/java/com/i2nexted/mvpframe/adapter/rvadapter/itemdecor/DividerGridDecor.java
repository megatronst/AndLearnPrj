package com.i2nexted.mvpframe.adapter.rvadapter.itemdecor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Administrator on 2017/5/23.
 */

public class DividerGridDecor extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;
    // 自定义的分割线宽高
    private int size = -1;
    public DividerGridDecor(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    public DividerGridDecor(Context context, int drawableId, int size){
        mDrawable = ContextCompat.getDrawable(context, drawableId);
        this.size = size;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 绘制水平分割线
     * */
    private void drawHorizontal(Canvas canvas, RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin ;
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + getDividerHeight(mDrawable);
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }

    }

    /**
     * 绘制垂直分割线
     * */
    private void drawVertical(Canvas canvas, RecyclerView parent){
        int chilCount = parent.getChildCount();
        for (int i=0; i<chilCount; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left +getDividerWidth(mDrawable);
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }

    /**
     * 获取列数
     * */
    private int getSpanCount(RecyclerView recyclerView){
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager)layoutManager).getSpanCount();
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager)layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 判断是否是最后一列
     * */
    private boolean isLastSpan(RecyclerView recyclerView, int positon, int spancount, int childCount){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            if ((positon + 1)%spancount == 0) return true;
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager)layoutManager).getOrientation();
            if (StaggeredGridLayoutManager.HORIZONTAL == orientation){
                childCount = childCount -childCount%spancount;
                if (positon >= childCount)return true;
            }else if (StaggeredGridLayoutManager.VERTICAL == orientation){
                if ((positon + 1)%spancount == 0) return true;
            }
        }

        return false;
    }

    /**
     * 判断是否最后一行
     * */
    private boolean isLastRaw(RecyclerView recyclerView, int position, int spanCount, int childCount){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            childCount = childCount - childCount%spanCount;
            if (position >= childCount)return true;
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager)layoutManager).getOrientation();
            if (StaggeredGridLayoutManager.HORIZONTAL == orientation){
                if ((position + 1)/spanCount == 0) return true;
            }else if (StaggeredGridLayoutManager.VERTICAL == orientation){
                childCount = childCount - childCount%spanCount;
                if (position >= childCount) return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        boolean isLastRaw = isLastRaw(parent, parent.getChildAdapterPosition(view), spanCount, childCount);
        boolean isLastSpan = isLastSpan(parent, parent.getChildAdapterPosition(view), spanCount, childCount);
        outRect.set(0, 0, isLastSpan ? 0 : mDrawable.getIntrinsicWidth(), isLastRaw ? 0 : mDrawable.getIntrinsicHeight());
    }

    /**
     * 获取分割线的高度
     * */
    private int getDividerHeight(Drawable mDrawable){
        if (mDrawable == null) return 0;
        return size > 0 ? size : mDrawable.getIntrinsicHeight();
    }

    /**
     * 获取分割线的宽度
     * */
    private int getDividerWidth(Drawable mDrawable){
        if (mDrawable == null) return 0;
        return size > 0 ? size : mDrawable.getIntrinsicWidth();
    }



}
