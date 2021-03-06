package com.example.yuzelli.bluetoolsvehiclemonitoring.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.bluetoolsvehiclemonitoring.R;
import com.example.yuzelli.bluetoolsvehiclemonitoring.base.BaseActivity;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment.PersonalFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment.SetFragment;
import com.example.yuzelli.bluetoolsvehiclemonitoring.view.fragment.WraningFragment;

public class MainActivity extends BaseActivity {


    //定义FragmentTabHost对象
    private FragmentTabHost tabHost;
    //定义一个布局
    private LayoutInflater layoutInflater;

    //定义数组来存放user的Fragment界面
    private Class fragmentArray[] = {SetFragment.class, WraningFragment.class, PersonalFragment.class};
    //定义数组来存放的按钮图片
    private int tabImageViewArray[] = { R.drawable.tab_set, R.drawable.tab_wraning,
           R.drawable.tab_mine};
    //Tab选项卡的文字
    private String tabtTextViewArray[] = {"设备" ,"警报设置","个人设置"};
    @Override
    protected int layoutInit() {
        return R.layout.activity_main;
    }

    @Override
    protected void binEvent() {
        initView();
    }

    @Override
    protected void fillData() {

    }


    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_pageContent);


        //得到fragment的个数
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabtTextViewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.main_tab_select_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_tabIcon);
        imageView.setImageResource(tabImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tabText);
        textView.setText(tabtTextViewArray[index]);

        return view;
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


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
