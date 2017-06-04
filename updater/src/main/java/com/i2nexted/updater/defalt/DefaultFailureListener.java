package com.i2nexted.updater.defalt;

import android.content.Context;
import android.widget.Toast;

import com.i2nexted.updater.interfaces.OnFailureListener;
import com.i2nexted.updater.UpdateError;

/**
 * Created by Administrator on 2017/5/26.
 */

public class DefaultFailureListener implements OnFailureListener {
    private Context context;

    public DefaultFailureListener(Context context) {
        this.context = context;
    }

    @Override
    public void onFailure(UpdateError updateError) {
        Toast.makeText(context, updateError.toString(), Toast.LENGTH_LONG).show();
    }
}
