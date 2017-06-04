package com.i2nexted.mvpframe.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.i2nexted.mvpframe.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Adapter_rv_pull_to_refresh extends BaseQuickAdapter<String, BaseViewHolder>{

    public Adapter_rv_pull_to_refresh( @Nullable List<String> data) {
        super(R.layout.item_test, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.id_tv_test, item);
    }

}
