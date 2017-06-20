package com.bookcl.empty;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private int mStackLevel=0;
    private int minfocount=0;
    private static final String KEY_INDX = "index";
    private static final String TAG = "[JamesL]-Main";
    public static final int REQUEST_CODE_INFO = (1024+0);
    public static final int REQUEST_CODE_NEWS = (1024+1);
    private static final int REQUEST_CODE_NEWS_INFO = (1024+2);
    public static final int REQUEST_CODE_NEWS_PAGER = (1024+3);

    public static final String KEY_SHOWTIME_ACT_EXT = "com.bookcl.empty.info_count";
    public static final String KEY_SHOWTIME_ACT_EXT_SHOW = "com.bookcl.empty.info_show";

    Button m_prev_bt;
    Button m_next_bt;

    ImageButton m_prev_img_bt;
    ImageButton m_next_img_bt;

    InfoArray[] mInfoAray = new InfoArray[] {
            new InfoArray(R.string.info_00, true),
            new InfoArray(R.string.info_01, true),
            new InfoArray(R.string.info_02, true),
            new InfoArray(R.string.info_03, true),
            new InfoArray(R.string.info_04, true),
            new InfoArray(R.string.info_05, true),
            new InfoArray(R.string.info_06, true),
            new InfoArray(R.string.info_07, true)
    };

    // Used to load the 'abc' and 'xyz' library on application startup.
    static {
        System.loadLibrary("abc");
        System.loadLibrary("xyz");
    }

    public native String stringFromJNI();
    public native String stringFromCPP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");

        float mAlpha=0.3f;

        if(savedInstanceState != null)
            minfocount = savedInstanceState.getInt(KEY_INDX,0);

        ImageView mImgview = (ImageView)findViewById(R.id.img_bg);
        mImgview.setAlpha(mAlpha);

        m_prev_bt = (Button) this.findViewById(R.id.main_prev);
        m_next_bt = (Button) this.findViewById(R.id.main_next);

        m_prev_img_bt = (ImageButton) this.findViewById(R.id.img_prev_bt);
        m_next_img_bt = (ImageButton) this.findViewById(R.id.img_next_bt);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        /*
        第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        输出详细的Bugly SDK的Log
        每一条Crash都会被立即上报
        自定义日志将会在Logcat中输出
        建议在测试阶段建议设置成true，发布时设置为false。
        */
        //CrashReport.initCrashReport(getApplicationContext(), "ca6c90db3b", true);
        CrashReport.initCrashReport(getApplicationContext());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
        outState.putInt(KEY_INDX,minfocount);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    void do_next_action(){
        minfocount++;
        if (minfocount > 7) {
            minfocount = 7;
            return;
        }

        if(minfocount == 7 ) {
            m_next_bt.setEnabled(false);
            m_next_img_bt.setBackgroundResource(R.drawable.to_right_ng);
            m_next_img_bt.setEnabled(false);
            m_next_img_bt.setSelected(false);
        }
        else if( minfocount > 0) {
            m_prev_bt.setEnabled(true);
            m_prev_img_bt.setBackgroundResource(R.drawable.to_left_btn_bg);
            m_prev_img_bt.setEnabled(true);
            m_prev_img_bt.setSelected(true);
        }
    }

    void do_prev_action(){
        minfocount--;
        if (minfocount < 0) {
            minfocount = 0;
            return;
        }
        if(minfocount == 0) {
            m_prev_bt.setEnabled(false);
            m_prev_img_bt.setBackgroundResource(R.drawable.to_left_ng);
            m_prev_img_bt.setEnabled(false);
            m_prev_img_bt.setSelected(false);
        }
        else if( minfocount< 7) {
            m_next_bt.setEnabled(true);
            m_next_img_bt.setBackgroundResource(R.drawable.to_right_btn_bg);
            m_next_img_bt.setEnabled(true);
            m_next_img_bt.setSelected(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");

        final TextView mAppInfo = (TextView) findViewById(R.id.appinfo);
        final TextView mTxInfo = (TextView) findViewById(R.id.txinfo);

        mAppInfo.setText(getDate() + "\n"+ stringFromJNI() + "\n" + stringFromCPP());

        mTxInfo.setText(mInfoAray[minfocount].getInfo());
        mTxInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                do_next_action();
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.i(TAG,"click text!");
            }
        });


        if(minfocount == 0) {
            m_prev_img_bt.setBackgroundResource(R.drawable.to_left_ng);
            m_prev_bt.setEnabled(false);
            m_prev_img_bt.setEnabled(false);
            m_prev_img_bt.setSelected(false);
        }

        if(minfocount == 7 ) {
            m_next_bt.setEnabled(false);
            m_next_img_bt.setBackgroundResource(R.drawable.to_right_ng);
            m_next_img_bt.setEnabled(false);
            m_next_img_bt.setSelected(false);
        }

        m_prev_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_prev_action();
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.i(TAG,"click prev button!");
            }
        });

        m_prev_img_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                do_prev_action();
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.i(TAG,"click prev button!");
            }
        });

        m_next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_next_action();
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.i(TAG,"click next button!");
            }
        });

        m_next_img_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_next_action();
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.i(TAG,"click next button!");
            }
        });



        Button frag_gd_man_bt = (Button) this.findViewById(R.id.gd_main_bt);
        frag_gd_man_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        Button mshowtime_bt = (Button) this.findViewById(R.id.showtime_bt);
        mshowtime_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent mshowtime_int = new Intent(MainActivity.this,ShowtimeActivity.class);
                //mshowtime_int.putExtra(KEY_SHOWTIME_ACT_EXT,minfocount);
                Intent mshowtime_int = ShowtimeActivity.newIntent(MainActivity.this,minfocount);
                //startActivity(mshowtime_int);
                startActivityForResult(mshowtime_int,REQUEST_CODE_INFO);
                Log.i(TAG,"Click show time button");
            }
        });


        Button mNewsbtn = (Button) this.findViewById(R.id.news_btn);
        mNewsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mNews_int = NewsActivity.newIntent(MainActivity.this,minfocount,null);
                startActivityForResult(mNews_int,REQUEST_CODE_NEWS);
                Log.i(TAG,"Click News button");
            }
        });

        Button mNewsInfobtn = (Button) this.findViewById(R.id.newsinfo_btn);
        mNewsInfobtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mNewsInfo_int = NewsInfoActivity.newIntent(MainActivity.this,minfocount);
                startActivityForResult(mNewsInfo_int,REQUEST_CODE_NEWS);
                Log.i(TAG,"Click NewsInfo button");
            }
        });

        Button mNewsPagerbtn = (Button) this.findViewById(R.id.news_pager_btn);
        mNewsPagerbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mNewsPager_int = NewsPagerActivity.newIntent(MainActivity.this,0);
                startActivityForResult(mNewsPager_int,REQUEST_CODE_NEWS_PAGER);
                Log.i(TAG,"Click NewsPager button");
            }
        });

    }


    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        
        // Create and show the dialog.
        DialogFragment newFragment = Dlg_Frgm_Main.newInstance(mStackLevel);
        newFragment.show(ft, "f_layout");
    }

    private int getRetFromIntent(Intent ret) {
        return ret.getIntExtra(KEY_SHOWTIME_ACT_EXT_SHOW,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"onActivityResult");
        Log.i(TAG,"Result code: " + requestCode);
        if(requestCode == REQUEST_CODE_INFO) {
            if(data != null) {
                int ret = getRetFromIntent(data);
                Log.i(TAG,"Get Result: " + ret);
            }
            else
                Log.i(TAG,"result data is null!");
        }
    }

    public String getDate(){
        String temp_str="";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        temp_str=sdf.format(dt);
        Log.i(TAG,"time is:" + temp_str);
        return temp_str;
    }

    public int WriteInfo(String path,String data){
        FileWriter fw;
        String getdata;
        if(data == null) {
            getdata = "This is a test by JamesL\n";
        }
        else {
            getdata = data;
        }

        try {
            fw = new FileWriter(path);
            fw.write(getdata);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
