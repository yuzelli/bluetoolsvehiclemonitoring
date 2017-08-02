package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.bean.UserInfo;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ok_password)
    EditText etOkPassword;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected int layoutInit() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void binEvent() {
        ivLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("修改密码");
        tvRight.setVisibility(View.GONE);

    }

    @Override
    protected void fillData() {

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, ChangePassActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_left, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_ok:
                String pass = etPassword.getText().toString().trim();
                String okpass = etOkPassword.getText().toString().trim();
                if (pass.equals("")) {
                    showToast("请输入密码");
                    return;
                }
                if (okpass.equals("")) {
                    showToast("请输入密码");
                    return;
                }
                if (!okpass.equals(pass)){
                    showToast("两次输入密码不一致");
                    return;
                }
                UserInfo user = (UserInfo) SharePreferencesUtil.readObject(this, ConstantsUtils.SP_LOGIN_USER_INFO);
                user.setPassword(pass);
                SharePreferencesUtil.saveObject(this,ConstantsUtils.SP_LOGIN_USER_INFO,user);
                showToast("密码已重置！");
                finish();
                break;


        }
    }
}
