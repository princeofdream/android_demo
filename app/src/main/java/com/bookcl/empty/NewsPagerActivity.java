package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class NewsPagerActivity extends FragmentActivity
        implements NewsPagerFragment.OnFragmentInteractionListener {

    private static final String TAG = "[JamesL]-FrgmPagerAct";
    public static final String KEY_NEWSPAGER_ACT_EXT = "com.bookcl.empty.newsinfo";

    private FragmentManager fm = null;
    private Fragment mFragment = null;

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mNewsPager_int = new Intent(packageContext,NewsPagerActivity.class);
        mNewsPager_int.putExtra(KEY_NEWSPAGER_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mNewsPager_int;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_fragment_base);

        fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(R.id.fragment_container);

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


    //@Override
    protected Fragment StartNewFragment() {
        Log.i(TAG,"StartNewFragment");
        return NewsPagerFragment.newInstance("","");
    }



    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG,"onFragmentInteraction");
    }
}
