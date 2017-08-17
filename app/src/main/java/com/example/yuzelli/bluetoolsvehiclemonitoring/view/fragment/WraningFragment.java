package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

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
    @BindView(R.id.toggle_on_off)
    ToggleButton toggle_on_off;




    @Override
    protected int layoutInit() {
        return R.layout.fragment_wraning;
    }

    @Override
    protected void bindEvent(View v) {
        ivLeft.setVisibility(View.GONE);
        tvCenter.setText("警告设置");
        tvRight.setVisibility(View.GONE);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        toggle_on_off.setChecked(doReaad());
        toggle_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                doSave(b);

            }
        });

        upDataView();


    }

    private void doSave(boolean flag) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("dates",MODE_PRIVATE).edit();
        editor.putBoolean("married", flag);
        editor.commit();
    }

    private boolean doReaad() {
        SharedPreferences pref = getActivity().getSharedPreferences("dates",MODE_PRIVATE);
        boolean married = pref.getBoolean("married",true);
        return married;
    }


    AudioManager mAudioManager ;

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
                showToast("报警时间设置为10s");
                break;
            case R.id.tv_long:
                SharePreferencesUtil.saveObject(getActivity(), ConstantsUtils.SP_TIME,true);
                showToast("报警时间设置为20s");
                break;
        }
    }
}
