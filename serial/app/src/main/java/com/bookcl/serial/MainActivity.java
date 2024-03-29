package com.bookcl.serial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;
import android_serialport_api.sample.Sending01010101Activity;
import android_serialport_api.sample.SerialPortActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("serial_jni");
    }

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;
    MainActivity.SendingThread mSendingThread;
    byte[] mBuffer;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private MainActivity.ReadThread mReadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyUSB0"), 115200, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mBuffer = new byte[4];
//            Arrays.fill(mBuffer, (byte) 0x55);
            if (mSerialPort != null) {
                mSendingThread = new MainActivity.SendingThread();
                mSendingThread.start();
            }
            mInputStream = mSerialPort.getInputStream();

            /* Create a receiving thread */
            mReadThread = new MainActivity.ReadThread();
            mReadThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendingThread extends Thread {
        @Override
        public void run() {
            int i0=0;
            int val = 0x33;

            while (!isInterrupted()) {
                mBuffer[0] = 0x31;
                mBuffer[1] = 0x32;
                mBuffer[2] = 0x33;
//                mBuffer[3] = 0x0D;
                val = 0x33 + i0;
                mBuffer[2] = (byte)val;

                try {
                    if (mOutputStream != null) {
                        Log.d("byJames", "fill data.........");
                        mOutputStream.write(mBuffer);
                    } else {
                        Log.d("byJames", "abort.........");
                        return;
                    }
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                i0++;
                if (i0 > 6)
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }


    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while(!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    protected void onDataReceived(final byte[] buffer, final int size) {
        Log.d("byJames", "get data == <" + bytes2Hex(buffer) + ">, size : " + size);
    }

    private static final char[] HEXES = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'
    };

    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        StringBuilder hex = new StringBuilder();

        for (byte b : bytes) {
            hex.append(HEXES[(b >> 4) & 0x0F]);
            hex.append(HEXES[b & 0x0F]);
        }

        return hex.toString();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}