package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;

public class ChangePassActivity extends BaseActivity {


    @Override
    protected int layoutInit() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void binEvent() {

    }

    @Override
    protected void fillData() {

    }

    public static void startAction(Context context){
        Intent intent = new Intent(context,ChangePassActivity.class);
        context.startActivity(intent);
    }
}
