package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;


public class NewsPagerActivity extends FragmentActivity implements NewsPagerFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[JamesL]-NewsPgrAct";
    private static final String KEY_NEWSPAGER_ACT_EXT_ID = "com.bookcl.empty.newspager_id";
    private UUID muuid;

    FragmentManager fm = getSupportFragmentManager();
    Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent mNewsPager_int = new Intent(packageContext,NewsPagerActivity.class);
        mNewsPager_int.putExtra(KEY_NEWSPAGER_ACT_EXT_ID,uuid);
        Log.i(TAG,"newIntent");
        return mNewsPager_int;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pager);

        Log.i(TAG,"onCreate");
        //muuid = (UUID) getIntent().getSerializableExtra(KEY_NEWSPAGER_ACT_EXT_ID);

        if(mFragment == null){
            mFragment = NewsPagerFragment.newInstance(1);
            fm.beginTransaction()
                    .add(R.id.fragment_container,mFragment)
                    .commit();
        }
    }

    public void onListFragmentInteraction() {
        Log.i(TAG,"onListFragmentInteraction");
    }
}
