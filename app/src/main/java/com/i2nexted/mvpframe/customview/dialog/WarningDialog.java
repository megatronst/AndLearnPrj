package com.i2nexted.mvpframe.customview.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.i2nexted.mvpframe.R;
import com.i2nexted.mvpframe.customview.dialog.basedialog.BaseDialog;

/**
 * Created by Administrator on 2017/5/25.
 */

public class WarningDialog extends BaseDialog {
    public WarningDialog(Context context) {
        super(context, R.layout.layout_notice_dialog);
    }

    @Override
    public void convertView() {
        getDialogViewHolder().setOnClick(R.id.id_tv_yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getContext(), "dismis", Toast.LENGTH_SHORT).show();
            }
        });
        setCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        setCancelble(true);
        setCancelbleOnTouchOutside(true);
    }

    public WarningDialog setTitle(String text){
        getDialogViewHolder().setText(R.id.id_tv_title, text);
        return this;
    }
    public WarningDialog setButton(String text){
        getDialogViewHolder().setText(R.id.id_tv_yes, text);
        return this;
    }
}
