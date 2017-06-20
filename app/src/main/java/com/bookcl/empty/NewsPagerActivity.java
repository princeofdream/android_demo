package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NewsPagerActivity extends FragmentBaseActivity
        implements NewsPagerFragment.OnFragmentInteractionListener {

    private static final String TAG = "[JamesL]-FrgmPagerAct";
    public static final String KEY_NEWSPAGER_ACT_EXT = "com.bookcl.empty.newsinfo";

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mNewsPager_int = new Intent(packageContext,NewsPagerActivity.class);
        mNewsPager_int.putExtra(KEY_NEWSPAGER_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mNewsPager_int;
    }

    @Override
    protected Fragment StartNewFragment() {
        Log.i(TAG,"StartNewFragment");
        return NewsPagerFragment.newInstance("","");
    }

    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG,"onFragmentInteraction");
    }
}
