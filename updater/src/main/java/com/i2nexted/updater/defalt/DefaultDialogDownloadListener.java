package com.i2nexted.updater.defalt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.i2nexted.updater.R;
import com.i2nexted.updater.interfaces.OnDownloadListener;

/**
 * Created by Administrator on 2017/5/27.
 */

public class DefaultDialogDownloadListener implements OnDownloadListener {
    private Context context;
    private ProgressDialog progressDialog;

    public DefaultDialogDownloadListener(Context context) {
        this.context = context;
    }

    @Override
    public void start() {
        if (context instanceof Activity && !((Activity)context).isFinishing()){
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage(context.getString(R.string.PROGRESS_UPDATE_DOWNLOAD_PROGRESS_MSG_TITLE));
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
            progressDialog = dialog;
        }
    }

    @Override
    public void onProgress(int progress) {
        if (progressDialog != null){
            progressDialog.setProgress(progress);
        }
    }

    @Override
    public void onFinish() {
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
