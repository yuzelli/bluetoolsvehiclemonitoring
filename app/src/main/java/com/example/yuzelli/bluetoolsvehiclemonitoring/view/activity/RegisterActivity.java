package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.bean.UserInfo;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.https.OkHttpClientManager;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.OtherUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;


public class RegisterActivity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.user_phone)
    EditText userPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.user_name)
    EditText username;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.bt_register)
    Button btRegister;



    @Override
    protected int layoutInit() {
        return R.layout.activity_register;
    }

    @Override
    protected void binEvent() {
        tvCenter.setText("用户注册");
        tvRight.setVisibility(View.GONE);


    }

    @Override
    protected void fillData() {

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_left, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.bt_register:
                String mobile = userPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String un = username.getText().toString().trim();
                if (verification(mobile, password, confirmPassword,un)) {
                    doRegisterAction(mobile, password,un);
                }
                break;
        }
    }

    //101：注册成功；102注册失败；103密码或者电话号码为空；104已注册过了
    private void doRegisterAction(String mobile, String password, String username) {
       UserInfo userInfo=new UserInfo (mobile,password,username);
        SharePreferencesUtil.saveObject(RegisterActivity.this,ConstantsUtils.SP_LOGIN_USER_INFO,userInfo);
        finish();

    }

    private boolean verification(String mobile, String password, String confirmPassword, String u_n) {
        boolean flag = true;
        if (u_n.equals("")) {
            showToast("请输入用户名！");
            flag = false;
        }
        if (mobile.equals("")) {
            showToast("请输入手机号！");
            flag = false;
        }
        if (password.equals("")) {
            showToast("请输入密码！");
            flag = false;
        }
        if (confirmPassword.equals("")) {
            showToast("请再次输入密码！");
            flag = false;
        }


        if (!OtherUtils.isPhoneEnable(mobile)) {
            showToast("输入手机号有误！");
            flag = false;
        }
        if (!password.equals(confirmPassword)) {
            showToast("两次输入密码不一致！");
            flag = false;
        }

        return flag;
    }

}
