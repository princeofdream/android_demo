package com.bookcl.webbase;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by JamesL on 2018/2/13.
 */

public class WebBaseActivity extends AppCompatActivity {

    TextView mTextView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextView.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextView.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextView.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_base);
        //mTextView = findViewById(R.id.sample_text);
        //mTextView.setText(R.string.app_name);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*
        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.fragment_web_base_home);

        if(mFragment == null){
            mFragment = WebBaseHomeFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_web_base_home,mFragment)
                    .commit();
        }
        */


    }
}
