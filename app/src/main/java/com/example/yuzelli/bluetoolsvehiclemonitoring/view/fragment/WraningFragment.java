package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 51644 on 2017/7/30.
 */

public class WraningFragment extends BaseFragment {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;


    @Override
    protected int layoutInit() {
        return R.layout.fragment_wraning;
    }

    @Override
    protected void bindEvent(View v) {
      ivLeft.setVisibility(View.GONE);
        tvCenter.setText("警告设置");
        tvRight.setVisibility(View.GONE);
    }

    @Override
    protected void fillData() {

    }

}
