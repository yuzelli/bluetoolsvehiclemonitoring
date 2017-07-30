package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.bean.UserInfo;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.ActivityCollectorUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.DialogUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity.ChangePassActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 51644 on 2017/7/6.
 */

public class PersonalFragment extends BaseFragment {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_password_change)
    TextView tvPasswordChange;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    private Context context;


    @Override
    protected int layoutInit() {
        return R.layout.fragment_personal;
    }


    @Override
    protected void bindEvent(View v) {
        context = getActivity();
        ivLeft.setVisibility(View.GONE);
        tvCenter.setText("个人中心");
        tvRight.setVisibility(View.GONE);
        updataView();
    }

    private void updataView() {
        UserInfo userInfo = (UserInfo) SharePreferencesUtil.readObject(getActivity(), ConstantsUtils.SP_LOGIN_USER_INFO);
        tvName.setText(userInfo.getUserName());
        tvPhone.setText(userInfo.getMobile());
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




    @OnClick({R.id.tv_password_change, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_password_change:
                UserInfo userInfo = (UserInfo) SharePreferencesUtil.readObject(getActivity(), ConstantsUtils.SP_LOGIN_USER_INFO);
                String pass = userInfo.getPassword();
                showChangePass( pass);
                break;
            case R.id.tv_exit:
                showExitLogin();
                break;
        }
    }

    private void showChangePass(final String pass) {
        new DialogUtils(getActivity(), R.layout.view_order_close_dialog) {
            @Override
            public void initLayout(ViewHelper helper, final Dialog dialog) {
                helper.setViewClick(R.id.img_cancel, new ViewHelper.ViewClickCallBack() {
                    @Override
                    public void doClickAction(View v) {
                        dialog.dismiss();
                    }
                });
                helper.setViewClick(R.id.tv_cancel, new ViewHelper.ViewClickCallBack() {
                    @Override
                    public void doClickAction(View v) {
                        dialog.dismiss();
                    }
                });
                final EditText et_password = helper.getView(R.id.et_password);
                helper.setViewClick(R.id.tv_ok, new ViewHelper.ViewClickCallBack() {
                    @Override
                    public void doClickAction(View v) {
                        if (et_password.getText().toString().equals(pass)){
                            ChangePassActivity.startAction(getActivity());

                            return;
                        }
                            dialog.dismiss();
                    }
                });
            }
        };
    }
}
