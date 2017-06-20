package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.UUID;

public class NewsInfoActivity extends FragmentActivity
        implements NewsInfoFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[JamesL]-NewsInfoAct";
    public static final String KEY_NEWSINFO_ACT_EXT = "com.bookcl.empty.newsinfo";
    public static final String KEY_NEWSINFO_ACT_EXT_ID = "com.bookcl.empty.newsinfo_id";
    private static int get_newsinfo_data = 0;

    FragmentManager fm = getSupportFragmentManager();
    Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

    public void onListFragmentInteraction(NewsInfo item) {
        Log.i(TAG,"James test communication!");
        Intent mNews_int = NewsActivity.newIntent(NewsInfoActivity.this,0, item.getId());
        startActivityForResult(mNews_int,MainActivity.REQUEST_CODE_NEWS);
        Log.i(TAG,"Click News button");
    }

    public void LongPressAction(NewsInfo info, int index) {
        Log.i(TAG,"James test long press. " + index);
        if(mFragment == null){
            //mFragment = new NewsFragment();
            //mFragment = new NewsInfoFragment();
            mFragment = NewsInfoFragment.newInstance(1,index);
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
        else
        {
            fm.beginTransaction().remove(mFragment);
            mFragment = null;
            mFragment = NewsInfoFragment.newInstance(1,index);
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        get_newsinfo_data = getIntent().getIntExtra(KEY_NEWSINFO_ACT_EXT, 0);
        setContentView(R.layout.activity_newsinfo);

        if(mFragment == null){
            //mFragment = new NewsFragment();
            //mFragment = new NewsInfoFragment();
            mFragment = NewsInfoFragment.newInstance(1,0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQUEST_CODE_NEWS){
            if(data != null) {
                String ret = data.getStringExtra(KEY_NEWSINFO_ACT_EXT_ID);
                UUID getuuid = UUID.fromString(ret);
                updateUI(getuuid);
            }
        }
    }

    public void updateUI(UUID uuid) {
        NewsInfoFragment.updateUI(uuid);
    }
}
