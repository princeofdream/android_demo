package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ShowtimeActivity extends AppCompatActivity {

    private static final String TAG = "[JamesL]-St";
    public static final String KEY_SHOWTIME_ACT_EXT = "com.bookcl.empty.info_count";
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
    }

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mshowtime_int = new Intent(packageContext,ShowtimeActivity.class);
        mshowtime_int.putExtra(KEY_SHOWTIME_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mshowtime_int;
    }
}
