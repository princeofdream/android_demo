package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;


public class NewsActivity extends AppCompatActivity
implements NewsFragment.OnFragmentInteractionListener {

    private static final String TAG = "[JamesL]-News";
    public static final String KEY_NEWS_ACT_EXT = "com.bookcl.empty.news";
    public static final String KEY_NEWS_ACT_EXT_ID = "com.bookcl.empty.news_id";
    private static int get_news_data = 0;
    private static String get_news_id = "";
    private static UUID mId = null;

    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG,"James test communication!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        get_news_data = getIntent().getIntExtra(KEY_NEWS_ACT_EXT, 0);
        get_news_id = getIntent().getStringExtra(KEY_NEWS_ACT_EXT_ID);
        if(get_news_id != null)
            mId = UUID.fromString(get_news_id);

        setContentView(R.layout.activity_news);

        FragmentManager fm = getSupportFragmentManager();
        Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

        if(mFragment == null){
            mFragment = NewsFragment.newInstance(mId);
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext, int mcount, UUID uuid) {
        Intent mNews_int = new Intent(packageContext,NewsActivity.class);
        mNews_int.putExtra(KEY_NEWS_ACT_EXT,mcount);

        if(uuid != null)
            mNews_int.putExtra(KEY_NEWS_ACT_EXT_ID,uuid.toString());

        Log.i(TAG,"newIntent");
        return mNews_int;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
