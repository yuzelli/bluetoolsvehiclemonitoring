package com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.bean.ToothInfoBean;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.BluetoothChatUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.DialogUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.VibratorUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity.DeviceSetActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

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
    @BindView(R.id.tv_mubiao)
    TextView tv_mubiao;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.img_all)
    ImageView img_all;
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
    @BindView(R.id.btn_blth_connect)
    Button btn_blth_connect;
    @BindView(R.id.tv_connect_state)
    TextView tv_connect_state;

    private MediaPlayer player;
    private final static String TAG = "SetFragment";
    //设置绑定的蓝牙名称
    public static String BLUETOOTH_NAME = "";

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Context mContext;

    private ProgressDialog mProgressDialog;
    private BluetoothChatUtil mBlthChatUtil;
    private ToothInfoBean toothInfo;
    private boolean isPlaySound = false;
    private Dialog dialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatUtil.STATE_CONNECTED:
                    String deviceName = msg.getData().getString(BluetoothChatUtil.DEVICE_NAME);
                    tv_connect_state.setText("已成功连接到设备" + deviceName);
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    showToast("连接失败");
                    break;
                case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    tv_connect_state.setText("与设备断开连接");
                    break;
                case BluetoothChatUtil.MESSAGE_READ: {
                    byte[] buf = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);
                    String str = new String(buf, 0, buf.length);
                    //Toast.makeText(getApplicationContext(), "读成功" + str, Toast.LENGTH_SHORT).show();

                    toothInfo = getToothInfo(str);
                    updataView();
                    break;
                }
                case BluetoothChatUtil.MESSAGE_WRITE: {
                    byte[] buf = (byte[]) msg.obj;
                    String str = new String(buf, 0, buf.length);
                    showToast("发送成功");
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private ToothInfoBean getToothInfo(String str) {
        str = str.substring(0, str.indexOf("}") + 1);
        ToothInfoBean t = new ToothInfoBean();
        try {
            JSONObject json = new JSONObject(str);
            double j = json.getDouble("jiaquan");
            t.setJaquan(j);
            t.setBen(json.getDouble("ben"));
            t.setCo2(json.getDouble("co2"));
            t.setCo(json.getDouble("co"));
            t.setSo2(json.getDouble("so2"));
            t.setNo(json.getDouble("no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return t;
    }

    private final static double jqw = 0.02f;
    private final static double jqj = 0.06f;

    private final static double benw = 1f;
    private final static double benj = 24f;

    private final static double co2w = 1000f;
    private final static double co2j = 2000f;

    private final static double cow = 200f;
    private final static double coj = 400f;

    private final static double so2w = 3f;
    private final static double so2j = 20f;

    private final static double now = 5f;
    private final static double noj = 20f;
    boolean isShowDialogFlag = false;
    boolean isShowSoundFlag = false;

    private void updataView() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        isShowDialogFlag = false;
        isShowSoundFlag = false;

        tvJiaquan.setText("甲醛：" + toothInfo.getJaquan() + "mg/m³");
        tvBen.setText("苯：" + toothInfo.getBen() + "mg/m³");
        tvCo2.setText("二氧化碳：" + toothInfo.getCo2() + "mg/m³");
        tvCo.setText("一氧化碳：" + toothInfo.getCo() + "mg/m³");
        tvSo2.setText("二氧化硫：" + toothInfo.getSo2() + "mg/m³");
        tvNo.setText("氮氧化合物：" + toothInfo.getNo() + "mg/m³");
        if (toothInfo.getJaquan() <= jqw) {
            imgJiaquan.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getJaquan() <= jqj) {
            imgJiaquan.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgJiaquan.setImageResource(R.drawable.ic_indicate_red);
            isShowSoundFlag = true;
        }
        if (toothInfo.getBen() <= benw) {
            imgBen.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getBen() <= benj) {
            imgBen.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgBen.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
        }
        if (toothInfo.getCo2() <= co2w) {
            imgCo2.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getCo2() <= co2j) {
            imgCo2.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgCo2.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
        }
        if (toothInfo.getCo() <= cow) {
            imgCo.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getCo() <= coj) {
            imgCo.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgCo.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
        }
        if (toothInfo.getSo2() <= so2w) {
            imgSo2.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getSo2() <= so2j) {
            imgSo2.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgSo2.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
        }
        if (toothInfo.getNo() <= now) {
            imgNo.setImageResource(R.drawable.ic_indicate_green);
        } else if (toothInfo.getNo() <= noj) {
            imgNo.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            imgNo.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
        }
        if (sumJingGao(toothInfo) < 1) {
            tv_all.setText("综合指数:良好");
            img_all.setImageResource(R.drawable.ic_indicate_green);
        } else if (sumbaojing(toothInfo) < 1) {
            tv_all.setText("综合指数:危险");
            img_all.setImageResource(R.drawable.ic_indicate_orgin);
            isShowDialogFlag = true;
        } else {
            img_all.setImageResource(R.drawable.ic_indicate_red);
            isShowDialogFlag = true;
            tv_all.setText("综合指数:警报");
        }
        if (isShowDialogFlag) {
            showWarnDialog();
        }
        if (isShowSoundFlag && !isPlaySound&&doReaad()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    showHintSound();
                }
            }).start();

        }


    }

    private void showWarnDialog() {
        new DialogUtils(getActivity(), R.layout.view_order_cloes_dialog2) {
            @Override
            public void initLayout(ViewHelper helper, final Dialog dialog) {
                helper.setViewClick(R.id.tv_ok, new ViewHelper.ViewClickCallBack() {
                    @Override
                    public void doClickAction(View v) {
                        dialog.dismiss();
                    }
                });
            }
        };

    }

    private double sumJingGao(ToothInfoBean toothInfo) {
        double a = toothInfo.getJaquan() / jqw + toothInfo.getBen() / benw + toothInfo.getCo2() / co2w
                + toothInfo.getCo() / cow + toothInfo.getSo2() / so2w + toothInfo.getNo() / now;
        return a;
    }

    private double sumbaojing(ToothInfoBean toothInfo) {
        double a = toothInfo.getJaquan() / jqj + toothInfo.getBen() / benj + toothInfo.getCo2() / co2j
                + toothInfo.getCo() / coj + toothInfo.getSo2() / so2j + toothInfo.getNo() / noj;
        return a;
    }

    private boolean doReaad() {
        SharedPreferences pref = getActivity().getSharedPreferences("dates",MODE_PRIVATE);
        boolean married = pref.getBoolean("married",true);
        return married;
    }

    @Override
    protected int layoutInit() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void bindEvent(View v) {
        ivLeft.setVisibility(View.GONE);
        tvCenter.setText("当前设备");
        tvRight.setText("目标设备");
        tvRight.setVisibility(View.VISIBLE);
        mContext = getActivity();

        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        10125);
            } else {
                //具有权限
                initBluetooth();
            }
        } else {
            //系统不高于6.0直接执行
            initBluetooth();
        }


        mBlthChatUtil = BluetoothChatUtil.getInstance(mContext);
        mBlthChatUtil.registerHandler(mHandler);
        mProgressDialog = new ProgressDialog(getActivity());

        player = MediaPlayer.create(getActivity(), R.raw.warnn);

    }

    //播放声音
    private void showHintSound() {
        if (isPlaySound) {
            return;
        }
        isPlaySound = true;


        int time = 0;
        boolean flag = (boolean) SharePreferencesUtil.readObject(getActivity(), ConstantsUtils.SP_TIME);
        if (flag) {
            time = 1000 * 20;
        } else {
            time = 1000 * 10;
        }

        player.reset();
        player = MediaPlayer.create(getActivity(), R.raw.warnn);//重新设置要播放的音频
        player.setLooping(true);
        player.start();
        VibratorUtil.Vibrate(getActivity(), time);   //震动100ms
        try {
            Thread.sleep(time);
            stopHintSound();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //暂停
    private void stopHintSound() {
        player.stop();
        VibratorUtil.stopVib();
        isPlaySound = false;
    }


    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {//设备不支持蓝牙
            showToast("设备不支持蓝牙");
            return;
        }
        //判断蓝牙是否开启
        if (!mBluetoothAdapter.isEnabled()) {//蓝牙未开启
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            //mBluetoothAdapter.enable();此方法直接开启蓝牙，不建议这样用。
        }
        //注册广播接收者，监听扫描到的蓝牙设备
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mBluetoothReceiver, filter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBlthChatUtil = null;
        getActivity().unregisterReceiver(mBluetoothReceiver);
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();//释放资源
    }


    @Override
    protected void fillData() {
        if (mBlthChatUtil != null) {
            if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                BluetoothDevice device = mBlthChatUtil.getConnectedDevice();
                if (null != device && null != device.getName()) {
                    tv_connect_state.setText("已成功连接到设备" + device.getName());
                } else {
                    tv_connect_state.setText("已成功连接到设备");
                }
            }
        }
        String mubiao = (String) SharePreferencesUtil.readObject(getActivity(), ConstantsUtils.SP_MU_BIAO_DEVICE_INFO);
        if (mubiao == null || mubiao.equals("")) {
            tv_mubiao.setText("目标设备：无");
        } else {
            BLUETOOTH_NAME = mubiao;
            tv_mubiao.setText("目标设备：" + BLUETOOTH_NAME);
        }
//        toothInfo = new ToothInfoBean(100,100,100,100,100,100);
//
//        updataView();
    }


    private void discoveryDevices() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (mBluetoothAdapter.isDiscovering()) {
            //如果正在扫描则返回
            return;
        }
        mProgressDialog.setTitle(getResources().getString(R.string.progress_scaning));
        mProgressDialog.show();
        // 扫描蓝牙设备
        mBluetoothAdapter.startDiscovery();

    }

    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mBluetoothReceiver action =" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null) return;
                Log.d(TAG, "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
                //蓝牙设备名称
                String name = scanDevice.getName();
                if (name != null && name.equals(BLUETOOTH_NAME)) {
                    mBluetoothAdapter.cancelDiscovery(); //取消扫描
                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));
                    mBlthChatUtil.connect(scanDevice);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            }
        }
    };

    @SuppressWarnings("unused")
    private void getBtDeviceInfo() {
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG, "bluetooth name =" + name + " address =" + address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size =" + devices.size());
        for (BluetoothDevice bonddevice : devices) {
            Log.d(TAG, "bonded device name =" + bonddevice.getName() +
                    " address" + bonddevice.getAddress());
        }
    }


    @OnClick({R.id.tv_right, R.id.btn_blth_connect, R.id.btn_blth_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                DeviceSetActivity.startAction(getActivity());
                break;
            case R.id.btn_blth_disconnect:
                if (mBlthChatUtil.getState() != BluetoothChatUtil.STATE_CONNECTED) {
                    showToast("蓝牙未连接");
                } else {
                    mBlthChatUtil.disconnect();
                }
                break;
            case R.id.btn_blth_connect:
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    showToast("蓝牙已连接");
                } else {
                    discoveryDevices();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10125) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意权限
              initBluetooth();
            } else {

            }
        }
    }

}
