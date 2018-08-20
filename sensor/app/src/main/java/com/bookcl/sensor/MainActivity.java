package com.bookcl.sensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.os.SystemProperties;
import android.widget.Toast;

import java.nio.file.spi.FileSystemProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sensor";
    private  SensorManager sm;

    private static float[] prevalue= {0,0,0};
    private static float[] deltaval= {0,0,0};
    private static int sampling_count = 0;
    private static float[] sample_value_x = {0,0,0,0,0};
    private static float[] sample_value_y = {0,0,0,0,0};
    private static float[] sample_value_z = {0,0,0,0,0};
    private static float[] sample_value_average = {0,0,0};
    private static float[] sample_value_angle = {0,0,0};

    float [] lb_move = {0,0};
    float [] lt_move = {0,0};
    float [] rb_move = {0,0};
    float [] rt_move = {0,0};

    boolean force_auto_tc = false;

    TextView edit_x;
    TextView edit_y;
    TextView edit_z;
    TextView t_delta_x;
    TextView t_delta_y;
    TextView t_delta_z;
    TextView full_delta_x;
    TextView full_delta_y;
    TextView full_delta_z;
    TextView t_angle_x;
    TextView t_angle_y;
    TextView t_angle_z;
    TextView t_value_lt;
    TextView t_value_lb;
    TextView t_value_rt;
    TextView t_value_rb;

    Button m_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        float font_sz=36.0f;
        setContentView(R.layout.activity_main);
        sm=(SensorManager)getSystemService(this.SENSOR_SERVICE);
        sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        edit_x = (TextView) findViewById(R.id.value_x);
        edit_y = (TextView) findViewById(R.id.value_y);
        edit_z = (TextView) findViewById(R.id.value_z);
        t_delta_x = (TextView) findViewById(R.id.delta_x);
        t_delta_y = (TextView) findViewById(R.id.delta_y);
        t_delta_z = (TextView) findViewById(R.id.delta_z);
        full_delta_x = (TextView) findViewById(R.id.full_delta_x);
        full_delta_y = (TextView) findViewById(R.id.full_delta_y);
        full_delta_z = (TextView) findViewById(R.id.full_delta_z);
        t_angle_x = (TextView) findViewById(R.id.value_angle_x);
        t_angle_y = (TextView) findViewById(R.id.value_angle_y);
        t_angle_z = (TextView) findViewById(R.id.value_angle_z);

        t_value_lt = (TextView) findViewById(R.id.value_lt);
        t_value_lb = (TextView) findViewById(R.id.value_lb);
        t_value_rt = (TextView) findViewById(R.id.value_rt);
        t_value_rb = (TextView) findViewById(R.id.value_rb);

        m_btn = (Button)findViewById(R.id.button);


        edit_x.setTextSize(font_sz);
        edit_y.setTextSize(font_sz);
        edit_z.setTextSize(font_sz);
        t_delta_x.setTextSize(font_sz);
        t_delta_y.setTextSize(font_sz);
        t_delta_z.setTextSize(font_sz);
        full_delta_x.setTextSize(font_sz);
        full_delta_y.setTextSize(font_sz);
        full_delta_z.setTextSize(font_sz);
        t_angle_x.setTextSize(font_sz);
        t_angle_y.setTextSize(font_sz);
        t_angle_z.setTextSize(font_sz);
        t_value_lt.setTextSize(font_sz);
        t_value_lb.setTextSize(font_sz);
        t_value_rt.setTextSize(font_sz);
        t_value_rb.setTextSize(font_sz);

        sm.registerListener(mListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        sm.registerListener(mListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("James","James click!!!");
                Intent intent = new Intent();
                intent.setAction("com.linkin.action.SRNSOR_TC");
                if(force_auto_tc) {
                    force_auto_tc=false;
                    intent.putExtra("SENSOR_AUTO_TC", "SENSOR_AUTO_TC_ENABLE");
                } else {
                    force_auto_tc=true;
                    intent.putExtra("SENSOR_AUTO_TC", "SENSOR_AUTO_TC_DISABLE");
                }
                MainActivity.this.sendBroadcast(intent);
                Toast.makeText(getApplicationContext(), "发送广播成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
           // Log.i(TAG,"onAccuracyChanged");
        }


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
           // Log.i(TAG,"onSensorChanged");
            int ret;
            float X_lateral = sensorEvent.values[0];
            float Y_longitudinal = sensorEvent.values[1];
            float Z_vertical = sensorEvent.values[2];

            ret = SensorSetDisplayArea(X_lateral, Y_longitudinal, Z_vertical);
            if(ret == 0) {
                doSomething(X_lateral, Y_longitudinal, Z_vertical);
            }
        }

    };

    public void doSomething(float x,float y,float z){
        edit_x.setText(sample_value_average[0]+"");
        edit_y.setText(sample_value_average[1]+"");
        edit_z.setText(sample_value_average[2]+"");
        t_delta_x.setText(deltaval[0]+"");
        t_delta_y.setText(deltaval[1]+"");
        t_delta_z.setText(deltaval[2]+"");
        full_delta_x.setText((sample_value_average[0] - 0)+"");
        full_delta_y.setText((sample_value_average[1] - 0)+"");
        //full_delta_z.setText((sample_value_average[2] - 9.6)+"");
        t_angle_x.setText(sample_value_angle[0]+"");
        t_angle_y.setText(sample_value_angle[1]+"");
        t_angle_z.setText(sample_value_angle[2]+"");
        t_value_lt.setText("(" + lt_move[0] +  "," + lt_move[1] + ")");
        t_value_lb.setText("(" + lb_move[0] +  "," + lb_move[1] + ")");
        t_value_rt.setText("(" + rt_move[0] +  "," + rt_move[1] + ")");
        t_value_rb.setText("(" + rb_move[0] +  "," + rb_move[1] + ")");
    }


    protected void onPause() {
        super.onPause();
        sm.unregisterListener(mListener);
    }


    public int SensorSetDisplayArea(float x, float y, float z)
    {
        float weightvalue = 0;
        float OneEightyOverPi = 57.29577957855f;

        if(sampling_count==1){
            sampling_count+=1;
            return 0;
        } else if(sampling_count < 3) {
            sample_value_x[sampling_count/2] = x;
            sample_value_y[sampling_count/2] = y;
            sample_value_z[sampling_count/2] = z;
            Log.d(TAG,"x: "+ x + "y: " +y + "z: "+ z);
            sampling_count+=1;
            return 1;
        }
        sampling_count = 0;

        sample_value_average[0] =0;
        sample_value_average[1] =0;
        sample_value_average[2] =0;

        for (int i0=0; i0 < 2; i0++) {
            sample_value_average[0] += sample_value_x[i0];
            sample_value_average[1] += sample_value_y[i0];
            sample_value_average[2] += sample_value_z[i0];
        }
        //Log.d(TAG,"x_total: "+ sample_value_average[0] + "y_total: " + sample_value_average[1] + "z_total: "+ sample_value_average[2]);

        sample_value_average[0] = sample_value_average[0]/2;
        sample_value_average[1] = sample_value_average[1]/2;
        sample_value_average[2] = sample_value_average[2]/2;

        //Log.d(TAG,"x_ave: "+ sample_value_average[0] + "y_ave: " + sample_value_average[1] + "z_ave: "+ sample_value_average[2]);
        //Log.d(TAG,"x_pre: "+ prevalue[0] + "y_pre: " + prevalue[1] + "z_pre: "+ prevalue[2]);

        deltaval[0] = sample_value_average[0] - prevalue[0];
        deltaval[1] = sample_value_average[1] - prevalue[1];
        deltaval[2] = sample_value_average[2] - prevalue[2];
        //Log.d(TAG,"x_delta: "+ deltaval[0] + "y_delta: " + deltaval[1] + "z_delta: "+ deltaval[2]);
        // delta_z = (float)Math.ads(delta_z);
        //weightvalue = 0.02f;


//        sample_value_angle[0] = (float)Math.atan2(x,Math.sqrt(y*y+z*z)) * OneEightyOverPi; //(180/pi)
//        sample_value_angle[1] = (float)Math.atan2(y,Math.sqrt(x*x+z*z)) * OneEightyOverPi; //(180/pi)
//        sample_value_angle[2] = (float)Math.atan2(z,Math.sqrt(y*y+x*x)) * OneEightyOverPi; //(180/pi)




        //if(deltaval[0] >= weightvalue || deltaval[1] >= weightvalue || deltaval[2] >= weightvalue)
        {
            float[] tan_angle = {0,0,0};

            tan_angle[0] = x/(float)Math.sqrt(y*y+z*z);
            tan_angle[1] = y/(float)Math.sqrt(x*x+z*z);
            tan_angle[2] = z/(float)Math.sqrt(x*x+y*y);

            prevalue[0] = sample_value_average[0];
            prevalue[1] = sample_value_average[1];
            prevalue[2] = sample_value_average[2];


            /*
            //angle of X to Z
            sample_value_angle[0] =(float)Math.atan2(-z, x) * OneEightyOverPi - 270;
            // normalize to 0 - 359 range
            while (sample_value_angle[0] >= 360) {
                sample_value_angle[0] -= 360;
            }
            while (sample_value_angle[0] < 0) {
                sample_value_angle[0] += 360;
            }

            Log.d(TAG, "sample_value_angle[0]: " + sample_value_angle[0] + " tan_angle[0]: " + tan_angle[0] );
            if((sample_value_angle[0] < 90 || sample_value_angle[0] > 270) && tan_angle[0] < 0.5 && tan_angle[0] > -0.5 ) {
                //when x changes
                // left bottom should move y = {864/[ctn(angle) - tan(angle)] - 480/[ctn(angle)*ctn(angle) - 1]}
                // right bottom should move (480 - y)tan(angle)
                //angle X to gravity
                sample_value_angle[0] = (float)Math.atan2(x,Math.sqrt(y*y+z*z)) * OneEightyOverPi; //(180/pi)
                if(sample_value_angle[0] > 0) {
                    lb_move[0] = 0; //x
                    lb_move[1] = -864 / (1 / tan_angle[0] - tan_angle[0]) - 480 / (1 / (tan_angle[0] * tan_angle[0]) - 1); //y
                    rb_move[0] = -(480 - lb_move[1]) * tan_angle[0]; //y
                    rb_move[1] = 0; //y

                    lt_move[0] = -rb_move[0]; //x
                    lt_move[1] = 0; //y
                    rt_move[0] = 0; //y
                    rt_move[1] = -lb_move[1]; //y
                } else {
                    lb_move[0] = 0; //x
                    lb_move[1] = -864 / (1 / tan_angle[0] - tan_angle[0]) - 480 / (1 / (tan_angle[0] * tan_angle[0]) - 1); //y
                    rb_move[0] = -(480 - lb_move[1]) * tan_angle[0]; //y
                    rb_move[1] = 0; //y

                    lt_move[0] = -rb_move[0]; //x
                    lt_move[1] = 0; //y
                    rt_move[0] = 0; //y
                    rt_move[1] = -lb_move[1]; //y
                }
            } else {

            }
            */

            //angle of Y to Z
            sample_value_angle[1] =(float)Math.atan2(-z, y) * OneEightyOverPi - 270;
            // normalize to 0 - 359 range
            while (sample_value_angle[1] >= 360) {
                sample_value_angle[1] -= 360;
            }
            while (sample_value_angle[1] < 0) {
                sample_value_angle[1] += 360;
            }

            if(sample_value_angle[1] < 90 || sample_value_angle[1] > 270) {
                //when x changes
                // left bottom should move y = {864/[ctn(angle) - tan(angle)] - 480/[ctn(angle)*ctn(angle) - 1]}
                // right bottom should move x = (480 - y)tan(angle)
                //angle Y to gravity
                sample_value_angle[1] = (float) Math.atan2(x, Math.sqrt(y * y + z * z)) * OneEightyOverPi; //(180/pi)
                if (sample_value_angle[1] > 0) {
                    lb_move[0] = 0;
                    lb_move[1] = 0;
                    rb_move[0] = 0;
                    rb_move[1] = 0;

                    lt_move[0] =  480-480/(float)Math.cos((float) Math.atan2(x, Math.sqrt(y * y + z * z)));
                    lt_move[1] = 0;
                    rt_move[0] = 480/(float)Math.cos((float) Math.atan2(x, Math.sqrt(y * y + z * z))) - 480;
                    rt_move[1] = 0;
                } else {
                    lb_move[0] = 0;
                    lb_move[1] = 0;
                    rb_move[0] = 0;
                    rb_move[1] = 0;

                    lt_move[0] = 0;
                    lt_move[1] = 0;
                    rt_move[0] = 0;
                    rt_move[1] = 0;
                }
            }

//            String value = SystemProperties.get("persist.sys.keystone.enable");
//            if ("true".equals(value)) {
//                TrapeziumCorrectionAlgorithm(x,y,z);
//            }
//            SystemProperties.set("persist.sys.keystone.lt",lt_move[0]+","+lt_move[1]);
//            SystemProperties.set("persist.sys.keystone.lb",lb_move[0]+","+lb_move[1]);
//            SystemProperties.set("persist.sys.keystone.rt",rt_move[0]+","+rt_move[1]);
//            SystemProperties.set("persist.sys.keystone.rb",rb_move[0]+","+rb_move[1]);

        }
        return 0;
    }



}
