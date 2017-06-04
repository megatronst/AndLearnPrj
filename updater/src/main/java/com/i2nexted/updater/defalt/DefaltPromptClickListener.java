package com.i2nexted.updater.defalt;

import android.content.DialogInterface;

import com.i2nexted.updater.interfaces.IUpdateAgent;

/**
 * Created by Administrator on 2017/5/26.
 */

public class DefaltPromptClickListener implements DialogInterface.OnClickListener {
    private final IUpdateAgent updateAgent;
    private final boolean isAutoDismiss;

    public DefaltPromptClickListener(IUpdateAgent updateAgent, boolean isAutoDismiss){
        this.updateAgent = updateAgent;
        this.isAutoDismiss = isAutoDismiss;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                updateAgent.update();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                updateAgent.ignore();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }

        if (isAutoDismiss){
            dialog.dismiss();
        }
    }
}
