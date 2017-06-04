package com.i2nexted.updater.defalt;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.i2nexted.updater.interfaces.OnDownloadListener;
import com.i2nexted.updater.R;

/**
 * Created by Administrator on 2017/5/27.
 * 通知栏的下载过程监听
 */

public class DefaultNotificationDownloadListener implements OnDownloadListener {

    private Context context;
    private int mNotifyId;
    private NotificationCompat.Builder builder;

    public DefaultNotificationDownloadListener(Context context, int mNotifyId) {
        this.context = context;
        this.mNotifyId = mNotifyId;
    }

    @Override
    public void start() {
        if (builder == null){
            String title = context.getString(R.string.PROGRESS_UPDATE_DOWNLOAD_MSG_TITLE) + context.getString(context.getApplicationInfo().labelRes);
            builder = new NotificationCompat.Builder(context);
            builder.setOngoing(true)
                    .setAutoCancel(false)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setTicker(title)
                    .setContentTitle(title);
        }
        onProgress(0);
    }

    @Override
    public void onProgress(int progress) {
        if (builder != null){
            if (progress > 0){
                builder.setPriority(Notification.PRIORITY_DEFAULT);
                builder.setDefaults(0);
            }
            builder.setProgress(100, progress ,false);
            NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(mNotifyId, builder.build());
        }
    }

    @Override
    public void onFinish() {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(mNotifyId);
    }
}
