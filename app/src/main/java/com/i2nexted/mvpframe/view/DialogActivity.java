package com.i2nexted.mvpframe.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.i2nexted.mvpframe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {
    // TODO 根据具体的ui设置实现提示框风格的acticity样式及具体功能
    @BindView(R.id.id_btn_yes)
    Button yesButton;
    @BindView(R.id.id_btn_no)
    Button noButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
    }

    @OnClick(R.id.id_btn_yes)
    public void yesButtonClicked(View v){
        finish();
        Intent intent = new Intent(DialogActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.id_btn_no)
    public void noButtonClicked(View v){
        finish();
    }
}
