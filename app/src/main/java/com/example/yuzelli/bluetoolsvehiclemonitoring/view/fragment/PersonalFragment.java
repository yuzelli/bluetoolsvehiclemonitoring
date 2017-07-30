package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.ActivityCollectorUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity.LoginActivity;

/**
 * Created by 51644 on 2017/7/6.
 */

public class PersonalFragment extends BaseFragment {


    private Context context;


    @Override
    protected int layoutInit() {
        return R.layout.fragment_personal;
    }



    @Override
    protected void bindEvent(View v) {
       context = getActivity();

    }
    @Override
    protected void fillData() {

    }



    private void showExitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("你确定退出当前用户");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharePreferencesUtil.saveObject(context, ConstantsUtils.SP_LOGIN_USER_INFO, null);
                ActivityCollectorUtil.finishAll();
                LoginActivity.startAction(context);

            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

}
