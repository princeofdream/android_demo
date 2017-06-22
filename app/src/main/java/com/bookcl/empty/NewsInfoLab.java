package com.bookcl.empty;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.SimpleFormatter;


/**
 * Created by JamesL on 2017/6/16.
 */

public class NewsInfoLab {
    private static NewsInfoLab sNewsInfoLab;

    private static final String TAG = "[JamesL]-NewsInfoLab";

    private static List<NewsInfo> mNewsInfo;
    private static final int NEW_EVENT = 100;

    public static NewsInfoLab get(Context mContext) {
        if(sNewsInfoLab == null) {
            Log.i(TAG,"Static NewsInfoLab is NULL!!");
            sNewsInfoLab = new NewsInfoLab(mContext);
        } else {
            Log.i(TAG,"get NewsInoLab!");
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

            long ltime=System.currentTimeMillis();
            Time mtime = new Time(ltime);

            Date mdate = new Date();
            //mdate.setYear(2015+rmd.nextInt(3));
            mdate.setMonth(rmd.nextInt(12));
            mdate.setDate(1+rmd.nextInt(28));
            newsinfo.setDate(mdate);
            mNewsInfo.add(newsinfo);
        }
    }

    public List<NewsInfo> getallNewsInfo(){
        return mNewsInfo;
    }

    public static NewsInfo getNewsInfo(UUID mId) {
        for (NewsInfo gNewsInfo : mNewsInfo) {
            if (gNewsInfo.getId().equals(mId)) {
                return gNewsInfo;
            }
        }
        return null;
    }

}
