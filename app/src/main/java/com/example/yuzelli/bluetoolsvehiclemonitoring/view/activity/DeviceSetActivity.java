package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.constants.ConstantsUtils;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.CommonAdapter;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.SharePreferencesUtil;
import com.example.yuzelli.bluetoolsvehiclemonitoring.utils.ViewHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceSetActivity extends BaseActivity {
    private String TAG = "--DeviceSet-->";
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.tv_mubiao)
    TextView tvMuBiao;
    @BindView(R.id.lv_device)
    ListView lv_device;

    private String mubiaoshebei_device ="";


    @Override
    protected int layoutInit() {
        return R.layout.activity_device_set;
    }

    @Override
    protected void binEvent() {
        ivLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("目标设备");
        tvRight.setVisibility(View.GONE);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void fillData() {

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DeviceSetActivity.class);
        context.startActivity(intent);
    }

    private List<String> searchDevice  = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter ;
    private void searchDervice() {
        IntentFilter filter = new IntentFilter();
//发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
//设备连接状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
        mBluetoothAdapter.startDiscovery();
    }
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice scanDevice = null;
            Log.d(TAG,"mBluetoothReceiver action ="+action);
            if(BluetoothDevice.ACTION_FOUND.equals(action)){//每扫描到一个设备，系统都会发送此广播。
                //获取蓝牙设备
                scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(scanDevice == null || scanDevice.getName() == null) {return;}
                Log.d(TAG, "name="+scanDevice.getName()+"address="+scanDevice.getAddress());
                //蓝牙设备名称
                String name = scanDevice.getName();
//                if(name != null && name.equals(BLUETOOTH_NAME)){
//                    mBluetoothAdapter.cancelDiscovery();
//                    //取消扫描
//                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));                   //连接到设备。
//                    mBlthChatUtil.connect(scanDevice);
//                }

                searchDevice.add(scanDevice.getName());
                upDataSearchListView();
                if (name==null||name.equals("")){
                    mBluetoothAdapter.cancelDiscovery();
                }
            }else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)){
                scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (scanDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        Log.d("BlueToothTestActivity", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.d("BlueToothTestActivity", "完成配对");
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Log.d("BlueToothTestActivity", "取消配对");
                    default:
                        break;
                }
            }
        }
    };

    private void upDataSearchListView() {
        lv_device.setAdapter(new CommonAdapter<String>(this,searchDevice, R.layout.cell_item) {
            @Override
            public void convert(ViewHolder helper, String item, int postion) {
                helper.setText(R.id.tv_name,item);
            }
        });
        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mubiaoshebei_device = searchDevice.get(i);
                tvMuBiao.setText("目标设备："+mubiaoshebei_device);
            }
        });
    }




    @OnClick({R.id.iv_left, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_ok:
                SharePreferencesUtil.saveObject(DeviceSetActivity.this, ConstantsUtils.SP_MU_BIAO_DEVICE_INFO,mubiaoshebei_device);
                finish();
                break;
        }
    }
}
