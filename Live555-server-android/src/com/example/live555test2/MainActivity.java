package com.example.live555test2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	static{
		System.loadLibrary("live555");
	}

	public static native String RtspServer();

	private ToggleButton mToggleButton;
	private Button mButton;
	private String ipAdress = "192.168.1.67";
	private Thread ServerThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton = (Button) findViewById(R.id.button1);

		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ServerThread = new Thread(new Runnable() {
					@Override
					public void run() {
						RtspServer();
					}
				});
				ServerThread.start();

			}

		});
	}

}
