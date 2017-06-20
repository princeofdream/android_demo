package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.UUID;


public class NewsPagerActivity extends FragmentActivity
        // implements NewsPagerFragment.OnListFragmentInteractionListener
        implements NewsFragment.OnFragmentInteractionListener {

    private static final String TAG = "[JamesL]-NewsPgrAct";
    private static final String KEY_NEWSPAGER_ACT_EXT_ID = "com.bookcl.empty.newspager_id";
    private UUID muuid;

    private ViewPager mViewPager;
    private List<NewsInfo> mNewsInfoList;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    FragmentManager fm;

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent mNewsPager_int = new Intent(packageContext,NewsPagerActivity.class);
        mNewsPager_int.putExtra(KEY_NEWSPAGER_ACT_EXT_ID,uuid);
        Log.i(TAG,"newIntent");
        return mNewsPager_int;
    }

    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG,"James test communication!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pager);

        Log.i(TAG,"onCreate");
        muuid = (UUID) getIntent().getSerializableExtra(KEY_NEWSPAGER_ACT_EXT_ID);
        if(muuid != null)
            Log.i(TAG,"get UUID: " + muuid.toString());
        else
            Log.i(TAG,"UUID is null");
        mNewsInfoList = NewsInfoLab.get(this).getallNewsInfo();
        //mViewPager = new ViewPager(this);
        //mViewPager.setId(R.id.newspager_viewpager);
        //setContentView(mViewPager);
        mViewPager = (ViewPager) findViewById(R.id.newspager_viewpager);


        fm = getSupportFragmentManager();

        mSectionsPagerAdapter=new SectionsPagerAdapter(fm);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                NewsInfo mNewsInfo = mNewsInfoList.get(position);
                return NewsFragment.newInstance(mNewsInfo.getId());
            }

            @Override
            public int getCount() {
                int ret = mNewsInfoList.size();
                return ret;
            }
        });
        */

        for (int i0 =0;i0< mNewsInfoList.size();i0++) {
            if(mNewsInfoList.get(i0).getId().equals(muuid)) {
                mViewPager.setCurrentItem(i0);
                break;
            }
        }
    }

    public class SectionsPagerAdapter extends /*FragmentPagerAdapter*/ FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            NewsInfo mNewsInfo = mNewsInfoList.get(position);
            return NewsFragment.newInstance(mNewsInfo.getId());
        }

        @Override
        public int getCount() {
            int ret = mNewsInfoList.size();
            return ret;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = mNewsInfoList.get(position).getTitle();
            return title;
        }
    }


}
