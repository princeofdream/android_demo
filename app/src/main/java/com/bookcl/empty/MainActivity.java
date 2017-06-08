package com.bookcl.empty;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private int mStackLevel=0;
    private int minfocount=0;
    private static final String KEY_INDX = "index";
    private static final String TAG = "[JamesL]-Main";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        if(savedInstanceState != null)
            minfocount = savedInstanceState.getInt(KEY_INDX,0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSa");
        outState.putInt(KEY_INDX,minfocount);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

        final Button m_prev_bt = (Button) this.findViewById(R.id.main_prev);
        final Button m_next_bt = (Button) this.findViewById(R.id.main_next);

        final TextView mTxInfo = (TextView) findViewById(R.id.txinfo);

        if(minfocount == 0)
            m_prev_bt.setEnabled(false);

        mTxInfo.setText(mInfoAray[minfocount].getInfo());

        mTxInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                minfocount++;
                if(minfocount == 7 )
                    m_next_bt.setEnabled(false);
                else if( minfocount > 0)
                    m_prev_bt.setEnabled(true);

                if (minfocount > 7) {
                    minfocount = 7;
                    return;
                }

                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.d(TAG,"click text!");
            }
        });

        m_prev_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minfocount--;
                if(minfocount == 0)
                    m_prev_bt.setEnabled(false);
                else if( minfocount< 7)
                    m_next_bt.setEnabled(true);
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.d(TAG,"click prev button!");
            }
        });


        if(minfocount == 7 )
            m_next_bt.setEnabled(false);
        m_next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minfocount++;
                if(minfocount == 7 )
                    m_next_bt.setEnabled(false);
                else if( minfocount > 0)
                    m_prev_bt.setEnabled(true);
                mTxInfo.setText(mInfoAray[minfocount].getInfo());
                Log.d(TAG,"click next button!");
            }
        });



        Button frag_gd_man_bt = (Button) this.findViewById(R.id.gd_main_bt);
        frag_gd_man_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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



}
