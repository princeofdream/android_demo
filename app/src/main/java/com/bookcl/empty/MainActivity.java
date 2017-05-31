package com.bookcl.empty;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ListFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("JamesL","onCreate");

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("JamesL","onResume");

        Button frag_gd_man_bt = (Button) this.findViewById(R.id.gd_main_bt);
        frag_gd_man_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JamesL", "--------------> 001 ");
            }
        });

        Button frag_gd_man_bt2 = (Button) this.findViewById(R.id.gd_main_bt2);
        frag_gd_man_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JamesL", "--------------> 002 ");
            }
        });

    }


}
