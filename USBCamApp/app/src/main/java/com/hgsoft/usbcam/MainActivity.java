package com.hgsoft.usbcam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "[USBCam]-Main";
    // Used to load the 'usbcam' library on application startup.
    static {
        System.loadLibrary("usbcam");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String usbcam_Capture();
    public native String usbcam_Record();

    @Override
    protected void onResume() {
        super.onResume();

        Button capture_btn = (Button) findViewById(R.id.capture_btn);
        capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Click capture");
                //usbcam_Capture();
            }
        });

        Button record_btn = (Button) findViewById(R.id.record_btn);
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Click Record");
                //usbcam_Capture();
            }
        });


    }
}
