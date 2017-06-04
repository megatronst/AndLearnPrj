package com.i2nexted.mvpframe.api.rx;


import android.content.Context;

import com.i2nexted.mvpframe.api.apiexcption.ExceptionEgine;
import com.i2nexted.mvpframe.api.netdialog.ProgressCancelListener;
import com.i2nexted.mvpframe.api.netdialog.ProgressDialogHandler;
import com.i2nexted.mvpframe.api.response.HttpResult;


import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/22.
 * rxjava 和 retrofit 结合使用， 网络请求的返回处理
 */

public abstract class HttpResultSubscriber<T>  extends Subscriber<HttpResult<T>> implements ProgressCancelListener{
    // 网络请求进度框管理
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private boolean isShowLoadingProgress = true;  //加载数据时是否显示loadingdialog，默认显示

    public HttpResultSubscriber(Context context, boolean isShowLoadingProgress){
        this.context = context;
        this.isShowLoadingProgress = isShowLoadingProgress;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);

    }

    public HttpResultSubscriber(Context context){
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        // 网络请求出错的时候隐藏进度框的显示
        if (isShowLoadingProgress){
            dismissProgressDialog();
        }
        // TODO 这里获取到了一个对应的网络请求过程中的异常，具体的时候根据异常的code来做对应的处理
        ExceptionEgine.handleException(e);
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        if (tHttpResult.isSuccess()) {
            onRequestSuccess(tHttpResult.getResult());
        } else {
            onRequestError(tHttpResult.getCode());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //  TODO 处理网络请求开始后的进度条显示,以及无网状态下取消订阅的操作
//        if (!NetUtil.isConnected(context)){
//            if (isUnsubscribed()){
//                unsubscribe();
//            }
//            throw new NetworkUnaviliableException();
//        }else{
//            // 显示进度框
//            showProgressDialog();
//        }
            // 显示进度框
            showProgressDialog();

    }

    @Override
    public void onCompleted() {
        // 网络请求完成后的进度条隐藏
        dismissProgressDialog();
    }

    /**
     * 在进度对话框被取消的时候取消订阅
     * */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()){
            this.unsubscribe();
            onRequestCancel();
        }
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }


    public abstract void onRequestSuccess(T t);
    public abstract void onRequestError(int status);
    public abstract void onRequestCancel();
}
