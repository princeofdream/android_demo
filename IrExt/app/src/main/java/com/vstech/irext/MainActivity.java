package com.vstech.irext;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;



import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    final String TAG="[JamesL] irext";
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("irext");
        System.loadLibrary("usbnok");
    }

    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";

    Button m_btn;
    Context context;

    private byte[] m_bytes;
    private static int TIMEOUT = 0;
    private boolean forceClaim = true;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"Gdetect usb");
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            //call method to set up device communication
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        m_btn = (Button)findViewById(R.id.m_btn);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        m_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usb_handle(context);
                Log.i(TAG,"Click show time button");
            }
        });
    }

    protected int usb_handle(Context mContext){
        /*
        UsbManager manager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0,
                new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        mContext.registerReceiver(mUsbReceiver, filter);

        int fd = -1;
        while(deviceIterator.hasNext()){
            UsbDevice device = deviceIterator.next();
            Log.i(TAG, device.getDeviceName() + " " + Integer.toHexString(device.getVendorId()) +
                    " " + Integer.toHexString(device.getProductId()));

            manager.requestPermission(device, mPermissionIntent);
            UsbDeviceConnection connection = manager.openDevice(device);
            if(connection != null){
                fd = connection.getFileDescriptor();
            } else
                Log.e(TAG, "UsbManager openDevice failed");
            break;
        }
        */
        Intent intent=getIntent();
        UsbDevice device = null;
        int fd = -1;
        boolean get_usb_device = false;
        //UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        //UsbDevice device = deviceList.get("CH551");
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0,
                new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        mContext.registerReceiver(mUsbReceiver, filter);


        while(deviceIterator.hasNext()){
            device = deviceIterator.next();
            Log.d(TAG,"--->>>"+device.getProductName());
            //your code
            if(device.getProductName()!= null && device.getProductName().equals("CH551"))
            {
                get_usb_device=true;
                break;
            }
        }

        if(get_usb_device && device != null){
            manager.requestPermission(device, mPermissionIntent);
            UsbDeviceConnection connection = manager.openDevice(device);
            if(connection != null){
                fd = connection.getFileDescriptor();
            } else {
                Log.e(TAG, "UsbManager openDevice failed");
                return -1;
            }

            /*try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //Log.d(TAG,"open usb device OK , fd = "+fd);
            //sethid(fd);
            m_bytes = new byte[10];
            m_bytes[0] = 0x12;
            m_bytes[1] = 0x13;
            m_bytes[2] = 0x23;
            m_bytes[3] = 0x45;
            m_bytes[4] = 0x56;
            m_bytes[5] = 0x16;
            m_bytes[6] = 0x63;
            m_bytes[7] = 0x64;
            m_bytes[8] = (byte)0xA5;
            m_bytes[9] = 0x66;


            UsbInterface intf = device.getInterface(0);
            UsbEndpoint endpoint = intf.getEndpoint(0);

            connection.claimInterface(intf, forceClaim);
            connection.bulkTransfer(endpoint, m_bytes, m_bytes.length, TIMEOUT);

            Log.d(TAG,"open usb device OK , fd = "+fd);
        }
        return 0;
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String sethid(int fd);
}
