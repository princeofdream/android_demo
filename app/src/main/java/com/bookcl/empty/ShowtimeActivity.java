package com.bookcl.empty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowtimeActivity extends AppCompatActivity {

    private static final String TAG = "[JamesL]-St";
    public static final String KEY_SHOWTIME_ACT_EXT = "com.bookcl.empty.info_count";
    public static final String KEY_SHOWTIME_ACT_EXT_SHOW = "com.bookcl.empty.info_show";
    private static int get_info_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_showtime);
        get_info_count = getIntent().getIntExtra(KEY_SHOWTIME_ACT_EXT, 0);
        Log.i(TAG,"Get info count: " + get_info_count);
        TextView mInfo = (TextView)findViewById(R.id.txinfo);
        mInfo.setText("Count: "+get_info_count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(ShowtimeActivity.this,"Get info Count: " + get_info_count,Toast.LENGTH_LONG).show();
        //setResult(Activity.RESULT_FIRST_USER);

        Button mPrev = (Button) findViewById(R.id.show_prev);
        mPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Click show prev button!");
                setShowtimeResult(100);
            }
        });

        Button mNext = (Button) findViewById(R.id.show_next);
        mNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Click show next button!");
                setShowtimeResult(101);
            }
        });

    }

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mshowtime_int = new Intent(packageContext,ShowtimeActivity.class);
        mshowtime_int.putExtra(KEY_SHOWTIME_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mshowtime_int;
    }

    private void setShowtimeResult(int ret)
    {
        Intent data = new Intent();
        data.putExtra(KEY_SHOWTIME_ACT_EXT_SHOW,ret);
        setResult(RESULT_OK,data);
    }
}
