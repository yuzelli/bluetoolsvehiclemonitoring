package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.bean.UserInfo;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.OtherUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;


import butterknife.BindView;
import butterknife.OnClick;



public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;

    @BindView(R.id.user_phone)
    EditText userPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.bt_login)
    Button btLogin;


    private Context context;
    private UserInfo userInfo;


    @Override
    protected int layoutInit() {
        return R.layout.activity_login;
    }

    @Override
    protected void binEvent() {
        ivLeft.setVisibility(View.GONE);
        tvRight.setText("注册");
        tvCenter.setText("用户登录");
        context = this;

    }

    @Override
    protected void fillData() {

    }


    @OnClick({R.id.tv_right, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                RegisterActivity.startAction(context);
                break;
            case R.id.bt_login:
                String mobile = userPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (verification(mobile, password)) {
                    doLoginAction(mobile, password);
                }
                break;
        }
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * code =201表示电话号码不对，code =202表示密码错误，code =203表示未知错误，
     */
    private void doLoginAction(String mobile, String password) {
      UserInfo userInfo = (UserInfo) SharePreferencesUtil.readObject(LoginActivity.this, ConstantsUtils.SP_LOGIN_USER_INFO);
     if (userInfo==null){
         showToast("用户未注册！");
         return;
     }
        if (userInfo.getMobile().equals(mobile)&&userInfo.getPassword().equals(password)){
           MainActivity.actionStart(LoginActivity.this);
            finish();
        }else {
            showToast("用户手机号或密码错误！");
            return;
        }
    }

    private boolean verification(String mobile, String password) {
        boolean flag = true;
        if (mobile.equals("")) {
            showToast("请输入手机号！");
            flag = false;
        }
        if (password.equals("")) {
            showToast("请输入密码！");
            flag = false;
        }

        if (!OtherUtils.isPhoneEnable(mobile)){
            showToast("输入手机号有误！");
            flag = false;
        }


            return flag;

    }



    long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

