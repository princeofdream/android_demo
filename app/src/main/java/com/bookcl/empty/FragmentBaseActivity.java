package com.bookcl.empty;


import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by JamesL on 2017/6/18.
 */

public abstract class FragmentBaseActivity extends FragmentActivity {

    public static String TAG = "[JamesL]-FrgmBaseAct";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_fragment_base);

        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

        if(mFragment == null){
            //mFragment = NewsInfoFragment.newInstance(1);
            mFragment = StartNewFragment();

            if(mFragment == null)
                return;

            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    protected abstract Fragment StartNewFragment();

}
