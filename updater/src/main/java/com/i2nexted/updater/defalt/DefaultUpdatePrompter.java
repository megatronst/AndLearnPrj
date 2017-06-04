package com.i2nexted.updater.defalt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.i2nexted.updater.interfaces.IUpdateAgent;
import com.i2nexted.updater.interfaces.IUpdatePrompter;
import com.i2nexted.updater.R;
import com.i2nexted.updater.UpdateInfo;

/**
 * Created by Administrator on 2017/5/26.
 */

public class DefaultUpdatePrompter implements IUpdatePrompter {
    private Context context;

    public DefaultUpdatePrompter(Context context) {
        this.context = context;
    }

    @Override
    public void prompt(IUpdateAgent agent) {
        // 如果是一个activity并且该activity正在进行finish的处理过程则直接返回，不显示任何更新提示
        if (context instanceof Activity && ((Activity)context).isFinishing()){
            return;
        }
        UpdateInfo updateInfo = agent.getInfo();
        // 设置更新提示对华框的显示内容
        String size = Formatter.formatFileSize(context, updateInfo.getSize());
        String content = context.getString(R.string.CONTENT_UPDATE_PROMPT_CONTENT, updateInfo.getVersionName(),size, updateInfo.getUpdateContent());
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.CONTENT_UPDATE_PROMPT_TITLE));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        // 设置对话框的布局显示
        float density = context.getResources().getDisplayMetrics().density;
        TextView contentTv = new TextView(context);
        contentTv.setMovementMethod(new ScrollingMovementMethod());
        contentTv.setVerticalScrollBarEnabled(true);
        contentTv.setTextSize(14);
        contentTv.setMaxHeight((int)(250*density));

        alertDialog.setView(contentTv, (int)(25*density), (int)(15*density), (int)(25*density), 0);

        DialogInterface.OnClickListener clickListener = new DefaltPromptClickListener(agent, true);
        if (updateInfo.isForce()){
            contentTv.setText(context.getString(R.string.CONTENT_UPDATE_PROMPT_CONTENT_FORCE, content));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.CONTENT_UPDATE_PROMPT_POSITIVE_YES), clickListener);
        }else {
            contentTv.setText(content);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.CONTENT_UPDATE_PROMPT_POSITIVE_UPDATE_NOW), clickListener);
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.CONTENT_UPDATE_PROMPT_NEGATIVE_UPDATE_LATER), clickListener);
            if (updateInfo.isIgnorable()){
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.CONTENT_UPDATE_PROMPT_NUTURAL_IGNORE), clickListener);
            }
        }

        alertDialog.show();





    }
}
