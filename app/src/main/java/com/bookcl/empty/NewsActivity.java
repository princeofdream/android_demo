package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NewsActivity extends FragmentActivity {

    private static final String TAG = "[JamesL]-News";
    public static final String KEY_NEWS_ACT_EXT = "com.bookcl.empty.news";
    private static int get_news_data = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        get_news_data = getIntent().getIntExtra(KEY_NEWS_ACT_EXT, 0);
        setContentView(R.layout.activity_news);

        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

        if(mFragment == null){
            mFragment = new NewsInfoFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mNews_int = new Intent(packageContext,NewsActivity.class);
        mNews_int.putExtra(KEY_NEWS_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mNews_int;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
