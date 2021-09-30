package com.rkdemo.gpiodemo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.net.Uri;
import android.content.Intent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.Map;

import com.rkdemo.gpiodemo.HardwareControler;

public class MainActivity extends Activity implements View.OnClickListener {
	EditText tx_gpio_pin;
    TextView tx_rd_view;
	EditText tx_wr_view;
	TextView tx_export_view;
	EditText tx_dirt_view;
    Button btn_rd, btn_wr, btn_dirt, btn_export;

    final static String TAG = "gpio_demo_james";

    static String rd_val = "0";
    static String wr_val = "0";
    static String dirt_val = "0";

	// static native String getStringFormC();

	// static {
	//     System.loadLibrary("friendlyarm-things");
	// }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

        tx_gpio_pin = (EditText) findViewById(R.id.tx_gpio_pin);
        tx_export_view = (TextView) findViewById(R.id.tx_export);
        tx_dirt_view = (EditText) findViewById(R.id.tx_dirt);
        tx_rd_view = (TextView) findViewById(R.id.tx_rd);
        tx_wr_view = (EditText) findViewById(R.id.tx_wr);

        btn_export = (Button) findViewById(R.id.btn_export);
        btn_dirt = (Button) findViewById(R.id.btn_dirt);
        btn_rd = (Button) findViewById(R.id.btn_rd);
        btn_wr = (Button) findViewById(R.id.btn_wr);

        btn_export.setOnClickListener(this);
        btn_dirt.setOnClickListener(this);
        btn_rd.setOnClickListener(this);
        btn_wr.setOnClickListener(this);
		HardwareControler hwc = new HardwareControler();

		//tx_gpio_pin.setText(hwc.stringFromJNI());
    }

    @Override
    public void onClick(View v) {
		HardwareControler hw_controller = new HardwareControler();
		String str_gpio_pin = tx_gpio_pin.getText().toString();
		int num_gpio_pin;
		int ret;

        Log.d(TAG,"on click btn");

		if ("".equals(str_gpio_pin)) {
			Toast.makeText(this, getString(R.string.no_pin_set),Toast.LENGTH_SHORT).show();
			return;
		}
		num_gpio_pin = Integer.parseInt(str_gpio_pin);
		if (num_gpio_pin <= 0) {
			Log.d(TAG,"pin not set!");
			Toast.makeText(this, getString(R.string.no_pin_num_set),Toast.LENGTH_SHORT).show();
			return ;
		}

        switch (v.getId()) {
            case R.id.btn_export:
			{
				String str_export_path = "/sys/class/gpio/gpio"+str_gpio_pin;
				Log.d(TAG, "export pin: " + str_gpio_pin);
				ret = hw_controller.native_exportGPIOPin(num_gpio_pin);
				ret = hw_controller.native_checkpath(str_export_path);
				if (ret == 0) {
					tx_export_view.setText(R.string.str_okay);
				} else {
					tx_export_view.setText(R.string.str_ng);
				}
				break;
			}

            case R.id.btn_dirt:
			{
				int num_gpio_direction;
				String str_gpio_direction = tx_dirt_view.getText().toString();

				if ("".equals(str_gpio_direction)) {
					num_gpio_direction = hw_controller.native_getGPIODirection(num_gpio_pin);
					if (num_gpio_direction == 0) {
						Log.d(TAG, "read direction: in");
						tx_dirt_view.setText("in");
					} else if (num_gpio_direction == 1) {
						Log.d(TAG, "read direction: out");
						tx_dirt_view.setText("out");
					} else {
						Log.d(TAG, "read direction: error");
						tx_dirt_view.setText("error");
					}
//					tx_dirt_view.setText(str_gpio_direction);
					break;
				} else if ("out".equals(str_gpio_direction) || "OUT".equals(str_gpio_direction)) {
					num_gpio_direction = 1;
				} else if ("in".equals(str_gpio_direction) || "IN".equals(str_gpio_direction)) {
					num_gpio_direction = 0;
				} else {
					num_gpio_direction = Integer.parseInt(str_gpio_direction);
				}

				if (num_gpio_direction < 0)
					break;

				Log.d(TAG, "on click direction...");
				hw_controller.native_setGPIODirection(num_gpio_pin, num_gpio_direction);
				num_gpio_direction = hw_controller.native_getGPIODirection(num_gpio_pin);
				tx_dirt_view.setText(num_gpio_direction+"");
				break;
			}

            case R.id.btn_rd:
			{
				int num_gpio_val;

                Log.d(TAG,"on click read btn");

				num_gpio_pin = hw_controller.native_getGPIOValue(num_gpio_pin);
                tx_rd_view.setText(num_gpio_pin + "");
                /*
                if ("0".equals(rd_val)) {
                    rd_val = "1";
                    tx_rd_view.setText(rd_val);
                } else {
                    rd_val = "0";
                    tx_rd_view.setText(rd_val);
                }
                 */
                break;
			}
            case R.id.btn_wr:
			{
				int num_gpio_val_wr;
				String str_gpio_val = tx_wr_view.getText().toString();

				if ("".equals(str_gpio_val)) {
					break;
				}

				num_gpio_val_wr = Integer.parseInt(str_gpio_val);
                if (num_gpio_val_wr < 0) {
					break;
				}
                if (num_gpio_val_wr == 0) {
					hw_controller.native_setGPIOValue(num_gpio_pin, num_gpio_val_wr);
				} else {
					hw_controller.native_setGPIOValue(num_gpio_pin, 1);
				}
                break;
			}
            default:
                Log.d(TAG, "unknow");
        }


    }

}
