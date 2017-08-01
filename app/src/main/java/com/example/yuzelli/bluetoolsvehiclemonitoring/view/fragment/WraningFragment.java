package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 51644 on 2017/7/30.
 */

public class WraningFragment extends BaseFragment {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_shout)
    TextView tv_shout;
    @BindView(R.id.tv_long)
    TextView tv_long;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mMusicVolume)
    SeekBar mMusicVolume;


    @Override
    protected int layoutInit() {
        return R.layout.fragment_wraning;
    }

    @Override
    protected void bindEvent(View v) {
        ivLeft.setVisibility(View.GONE);
        tvCenter.setText("警告设置");
        tvRight.setVisibility(View.GONE);
        upDataView();
    }

    AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

    private void upDataView() {

        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //获取当前音乐音量
        mMusicVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)); //SEEKBAR设置为音量的最大阶数
        mMusicVolume.setProgress(mVolume); //设置seekbar为当前音量进度
        mMusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); //拖动seekbar时改变音量
            }
        });
    }

    @Override
    protected void fillData() {

    }
    @OnClick({R.id.tv_shout, R.id.tv_long})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shout:
                SharePreferencesUtil.saveObject(getActivity(), ConstantsUtils.SP_TIME,false);
                break;
            case R.id.tv_long:
                SharePreferencesUtil.saveObject(getActivity(), ConstantsUtils.SP_TIME,true);
                break;
        }
    }
}
