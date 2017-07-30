package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 51644 on 2017/7/30.
 */

public class SetFragment extends BaseFragment {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_jiaquan)
    TextView tvJiaquan;
    @BindView(R.id.img_jiaquan)
    ImageView imgJiaquan;
    @BindView(R.id.tv_ben)
    TextView tvBen;
    @BindView(R.id.img_ben)
    ImageView imgBen;
    @BindView(R.id.tv_co2)
    TextView tvCo2;
    @BindView(R.id.img_co2)
    ImageView imgCo2;
    @BindView(R.id.tv_co)
    TextView tvCo;
    @BindView(R.id.img_co)
    ImageView imgCo;
    @BindView(R.id.tv_so2)
    TextView tvSo2;
    @BindView(R.id.img_so2)
    ImageView imgSo2;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.tv_content)
    LinearLayout tvContent;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;


    @Override
    protected int layoutInit() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void bindEvent(View v) {
        ivLeft.setVisibility(View.GONE);
        tvCenter.setText("当前设备");
        tvRight.setText("设备设置");

    }

    @Override
    protected void fillData() {

    }
    @OnClick(R.id.tv_right)
    public void onViewClicked() {
    }
}
