package com.i2nexted.mvpframe.app;

import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.i2nexted.mvpframe.util.Utils;
import com.i2nexted.mvpframe.view.DialogActivity;

/**
 * Created by Administrator on 2017/5/3.
 */

public class MyApplication extends Application {
    private static MyApplication appContext;
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        initFresco();
        initUtils();
        handleException();
    }

    /**
     * 获取全局context
     */
    public static MyApplication getMyApplication() {
        return appContext;
    }

    /**
     * 初始化Fresco框架
     */
    private void initFresco() {
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));  //初始化Fresco
    }

    /**
     * 初始化全局共用的工具
     * */
    private void initUtils(){
        Utils.init(this);
    }

    /**
     * 处理app异常崩溃的情况
     */
    private void handleException() {
//         Thread.setDefaultUncaughtExceptionHandler(restartHandler);

    }

    /**
     * 实际执行app重启操作
     */
    public void restartApp() {
//        AlarmManager mgr = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(appContext, DialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        new WarningDialog(this).setTitle("application title").setButton("button").setDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Intent intent = new Intent(MyApplication.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        }).showInApplication().showDialog(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
