package com.bookcl.empty;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * Created by JamesL on 2017/6/16.
 */

public class NewsInfoLab {
    private NewsInfoLab sNewsInfoLab;

    private static final String TAG = "[JamesL]-NewsInfoLab";

    private List<NewsInfo> mNewsInfo;
    private static final int NEW_EVENT = 100;

    public NewsInfoLab get(Context mContext) {
        if(sNewsInfoLab == null) {
            sNewsInfoLab = new NewsInfoLab(mContext);
        }
        return sNewsInfoLab;
    }

    private NewsInfoLab(Context mContext) {
        mNewsInfo = new ArrayList<>();

        for(int i0=0; i0 < NEW_EVENT; i0++) {
            NewsInfo newsinfo = new NewsInfo();
            newsinfo.setTitle("News # " + i0);
            Random rmd = new Random();
            //Log.i(TAG,"get ramdom number: " + rmd.nextInt());

            newsinfo.setRead(rmd.nextBoolean());
            mNewsInfo.add(newsinfo);
        }
    }

    public List<NewsInfo> getallNewsInfo(){
        return mNewsInfo;
    }

    public NewsInfo getNewsInfo(UUID mId) {
        for (NewsInfo gNewsInfo : mNewsInfo) {
            if (gNewsInfo.getId().equals(mId)) {
                return gNewsInfo;
            }
        }
        return null;
    }

}
