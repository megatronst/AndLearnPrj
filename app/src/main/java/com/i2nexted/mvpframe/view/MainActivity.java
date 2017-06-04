package com.i2nexted.mvpframe.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.i2nexted.mvpframe.R;
import com.i2nexted.mvpframe.api.response.HttpResult;
import com.i2nexted.mvpframe.api.retrofit.ApiEngine;
import com.i2nexted.mvpframe.api.rx.HttpResultSubscriber;
import com.i2nexted.mvpframe.api.rx.RxSchedulerHelper;
import com.i2nexted.mvpframe.mvp.BasePresenter;
import com.i2nexted.mvpframe.mvp.baseact.BaseActivity;
import com.i2nexted.mvpframe.network.ItemGank;

import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.id_btn_showdialog)
    Button showDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    @Override
    public void initParam() {

    }

    @Override
    public void initView() {

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiEngine.getInstance()
                        .getApiService()
                        .getGank(10,1)
                        .compose(RxSchedulerHelper.<HttpResult<List<ItemGank>>>io_main())
                        .subscribe(new HttpResultSubscriber<List<ItemGank>>(MainActivity.this) {
                    @Override
                    public void onRequestSuccess(List<ItemGank> itemGank) {
                        Toast.makeText(MainActivity.this, itemGank.size()+ " success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRequestError(int status) {
                        Toast.makeText(MainActivity.this, status+ "error ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onRequestCancel() {
                        Toast.makeText(MainActivity.this, "cancel ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initLoadData() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }


}

