package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceSetActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_blue_setting)
    TextView tvBlueSetting;
    @BindView(R.id.tv_add_device)
    TextView tvAddDevice;
    @BindView(R.id.tv_remove_device)
    TextView tvRemoveDevice;
    @BindView(R.id.tv_change_device)
    TextView tvChangeDevice;

    @Override
    protected int layoutInit() {
        return R.layout.activity_device_set;
    }

    @Override
    protected void binEvent() {

    }

    @Override
    protected void fillData() {

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DeviceSetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_blue_setting, R.id.tv_add_device, R.id.tv_remove_device, R.id.tv_change_device})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_blue_setting:
                break;
            case R.id.tv_add_device:
                break;
            case R.id.tv_remove_device:
                break;
            case R.id.tv_change_device:
                break;
        }
    }
}
