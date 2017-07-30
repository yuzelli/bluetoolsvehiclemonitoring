package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;

public class DeviceSetActivity extends BaseActivity {

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

    public static void startAction(Context context){
        Intent intent = new Intent(context,DeviceSetActivity.class);
        context.startActivity(intent);
    }
}
