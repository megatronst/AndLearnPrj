package com.i2nexted.mvpframe.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 * 运用建造者模式构建，用于存在加载更多和刷新数据的时候，简化实现流程
 * 注意：
 * 刷新和加载更多最终通过结果回调传递给使用者数据都是网络请求获得的数据，之前的老数据无关。
 * 使用者需要自己处理数据的追加和清除
 */

public class PageHelper<T> {
    public static final int RESULT_STATE_ERROR = 0;
    public static final int RESULT_STATE_COMPLETE = 1;
    public static final int RESULT_STATE_END = 2;
    public static final boolean REFRESH = true;
    public static final boolean LOAD_MORE = false;

    private boolean isRefresh;
    // 当前的页码
    private int currentPage = 1;
    // 每页的大小
    private int pageSize;
    // 请求的返回数据,此列表中的数据是控件中显示的最终数据，
    // 所获取的数据的具体类型
    private Class clazz;
    private Context context;

    // 设置数据请求的具体过程的回调
    public interface OnLoadDataCallBack {
        void loadData(int currentPage, int pageSize);
    }

    // 数据处理完成后的监听,使用者获取数据并进行显示处理
    private OnLoadDataFinishedListener<T> onLoadDataFinishedListener;

    public interface OnLoadDataFinishedListener<E> {
        void onLoadPagedDataFinished(List<E> resultList);
    }

    // 网络请求完成后的监听，用于处理控件的状态更新
    private ViewUpdateCallBack viewUpdateCallBack;

    public interface ViewUpdateCallBack {
        void updateRefreshState(int resultState);

        void updateLoadState(int resultState);
    }

    // 数据请求结束后的相关处理
    private final int LOAD_MORE_FINISHED = 0X3333;
    private final int REFRESH_FINISHED = 0X3334;
    private final int LOAD_DATA_ERROR = 0X3335;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_MORE_FINISHED:
                    whenLoadMoreFinished(msg);
                    break;
                case REFRESH_FINISHED:
                    whenRefreshFinished(msg);
                    break;
                case LOAD_DATA_ERROR:
                    whenLoadDataError();
                    break;
            }
            return false;
        }
    });

    public PageHelper(Context context) {
        this.context = context;
    }




    /**
     * 处理刷新完成后的操作,此方法在handler中调用
     *
     * @param message 对应handler的消息
     */
    private void whenRefreshFinished(Message message) {
        // 处理刷新操作完成后的到的数据，并通知控件显示
        if (onLoadDataFinishedListener != null) {
            ItemPageResponse itemPageResponse = (ItemPageResponse) message.obj;
            List<T> resultList = itemPageResponse.getList(clazz);
            onLoadDataFinishedListener.onLoadPagedDataFinished(resultList);
        }
        // 根据请求结果更新控件的刷新状态
        notifyViewUpdate(RESULT_STATE_COMPLETE);
    }

    /**
     * 处理加载完成后的操作，此方法在handler中调用
     *
     * @param message 对应handler的消息
     */
    private void whenLoadMoreFinished(Message message) {
        ItemPageResponse itemPageResponse = (ItemPageResponse) message.obj;
        // 对加载更多网络请求数据的处理
        if (onLoadDataFinishedListener != null) {
            // 将数据转化成对应类型数据列表
            List<T> resultList = itemPageResponse.getList(clazz);
            onLoadDataFinishedListener.onLoadPagedDataFinished(resultList);
        }
        // 加载更多的请求完成过后根据请求的结果通知控件刷新
        notifyViewUpdate(itemPageResponse.isHasNextPage() ? RESULT_STATE_COMPLETE : RESULT_STATE_END);
    }

    /**
     * 在加载数据错误时调用
     */
    private void whenLoadDataError() {
        // 在加载出错的时候修正当前页数
        if (!isRefresh) {
            currentPage--;
        }
        //
        notifyViewUpdate(RESULT_STATE_ERROR);
    }

    /**
     * 在网络请求完成的时候通知控件状态更新
     */
    private void notifyViewUpdate(int resultState) {
        if (viewUpdateCallBack != null) {
            if (isRefresh) {
                viewUpdateCallBack.updateRefreshState(resultState);
            } else {
                viewUpdateCallBack.updateLoadState(resultState);
            }
        }
    }


    /****************************************供外部调用的方法*************************************/

    /**
     * 设置子类的具体类，用于将数据解析成具体的类型
     */
    public PageHelper setClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * 设置每页大小
     */
    public PageHelper setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 设置数据加载结果监听
     */
    public PageHelper setOnloadFinishedListener(OnLoadDataFinishedListener onloadFinishedListener) {
        this.onLoadDataFinishedListener = onloadFinishedListener;
        return this;
    }

    /**
     * 设置控件更新的回调
     */
    public PageHelper setViewUpdateCallback(ViewUpdateCallBack viewUpdateCallback) {
        this.viewUpdateCallBack = viewUpdateCallback;
        return this;
    }

    /**
     * 用于请求数据时调用的方法
     */
    public void loadData(final boolean isRefresh, final OnLoadDataCallBack callBack) {
        // 判断网络状况，只有在网络可用的情况下才进行加载和刷新操作
        this.isRefresh = isRefresh;
        currentPage = isRefresh ? 1 : ++currentPage;
        callBack.loadData(currentPage, pageSize);

    }

    /**
     * 通知此工具类数据加载完成
     */
    public void loadFinishNotify(ItemPageResponse itemPageResponse) {
        Message message = new Message();
        message.obj = itemPageResponse;
        message.what = isRefresh ? REFRESH_FINISHED : LOAD_MORE_FINISHED;
        mHandler.sendMessage(message);
    }

    /**
     * 当加载数据出错时的处理
     */
    public void loadFinishError() {
        Message message = new Message();
        message.what = LOAD_DATA_ERROR;
        mHandler.sendMessage(message);
    }



}
