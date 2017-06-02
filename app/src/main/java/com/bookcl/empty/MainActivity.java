package com.bookcl.empty;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
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
    int mStackLevel=0;

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
                showDialog();
            }
        });

        Log.d("JamesL", "--------------> 004 ");

    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        /*
        Fragment prev = getFragmentManager().findFragmentByTag("f_layout");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        */

        // Create and show the dialog.
        DialogFragment newFragment = Dlg_Frgm_Main.newInstance(mStackLevel);
        newFragment.show(ft, "f_layout");
    }


}
