package com.example.watermask;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.ibox.WaterMaskManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WaterMaskActivity extends Activity {

	private final String TAG = "watermask";
	private WaterMaskManager mWaterMaskManager = null;
	private Button mButton;
	private Button mButton2;
	byte[] cmd = {0x01,0x03};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watermask);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


		mWaterMaskManager = (WaterMaskManager)getSystemService(Context.WaterMask_SERVICE);
		mButton = (Button)findViewById(R.id.button1);
		mButton2 = (Button)findViewById(R.id.button);

		mButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				int ret = mWaterMaskManager.getPreviewWatermaskStat();
				Log.d(TAG,"get preview value:" + ret);
				TextView textView;
				textView = (TextView) findViewById(R.id.textView);
				textView.setText("get preview val: " + ret);
			}
		});
		mButton2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				mWaterMaskManager.setPreviewWatermaskStat(1);
				TextView textView;
				textView = (TextView) findViewById(R.id.textView);
				textView.setText("val ++");
			}
		});
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("obu data");
		this.registerReceiver(new MyBroadcastReciver(), intentFilter);
    }

	private class MyBroadcastReciver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG,"test .......");
			/*
			if(action.equals("obu data")) {
			String data = intent.getStringExtra("data");
			//在控制台显示接收到的广播内容
			Log.i(TAG,"data==>"+data);
			}
			*/
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.watermask, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_watermask, container, false);
            return rootView;
		}
	}

}
