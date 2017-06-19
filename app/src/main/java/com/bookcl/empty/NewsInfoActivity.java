package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class NewsInfoActivity extends FragmentActivity
        implements NewsInfoFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[JamesL]-NewsInfoAct";
    public static final String KEY_NEWSINFO_ACT_EXT = "com.bookcl.empty.newsinfo";
    private static int get_newsinfo_data = 0;

    public void onListFragmentInteraction(NewsInfo item) {
        Log.i(TAG,"James test communication!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        get_newsinfo_data = getIntent().getIntExtra(KEY_NEWSINFO_ACT_EXT, 0);
        setContentView(R.layout.activity_newsinfo);

        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

        if(mFragment == null){
            //mFragment = new NewsFragment();
            //mFragment = new NewsInfoFragment();
            mFragment = NewsInfoFragment.newInstance(1);
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mNews_int = new Intent(packageContext,NewsInfoActivity.class);
        mNews_int.putExtra(KEY_NEWSINFO_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mNews_int;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
