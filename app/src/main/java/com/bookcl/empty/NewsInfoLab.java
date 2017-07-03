package com.bookcl.empty;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import com.bookcl.empty.NewsInfoDBSchema.NewsInfoTable;

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

    private static List<NewsInfo> mNewsInfoList;
    private static final int NEW_EVENT = 10;
    private Context mCtx;
    private SQLiteDatabase mDB;

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
        mNewsInfoList = new ArrayList<>();

        /* init DB */
        mCtx = mContext.getApplicationContext();
        mDB = new NewsInfoBaseHelper(mCtx).getWritableDatabase();
        Log.i(TAG,"NewsInfoLab");

        for(int i0=0; i0 < NEW_EVENT; i0++) {
            NewsInfo newsinfo = new NewsInfo();
            newsinfo.setTitle("News # " + i0);
            Random rmd = new Random();
            newsinfo.setRead(rmd.nextBoolean());

            Date mdate = new Date();
            //mdate.setYear(2015+rmd.nextInt(3));
            mdate.setMonth(rmd.nextInt(12));
            mdate.setDate(1+rmd.nextInt(28));
            newsinfo.setDate(mdate);

            AddNewsInfo(newsinfo);
        }
    }

    public List<NewsInfo> getNewsInfoList(){
        return mNewsInfoList;
    }

    public static NewsInfo getNewsInfo(UUID mId) {
        for (NewsInfo gNewsInfo : mNewsInfoList) {
            if (gNewsInfo.getId().equals(mId)) {
                return gNewsInfo;
            }
        }
        return null;
    }

    public int AddNewsInfo(NewsInfo newsinfo) {
        Log.i(TAG,"AddNewsInfo");

        if(newsinfo == null)
            return -1;

        ContentValues values = getContentValues(newsinfo);
        mDB.insert(NewsInfoTable.NI_NAME,null,values);

        if (newsinfo.getTitle() == null)
            newsinfo.setTitle("[null] News # " + mNewsInfoList.size());
        mNewsInfoList.add(newsinfo);
        return 0;
    }

    public int UpdateNewsInfo(NewsInfo newsinfo) {
        String mId = newsinfo.getId().toString();
        ContentValues values = getContentValues(newsinfo);

        mDB.update(NewsInfoTable.NI_NAME,values,NewsInfoTable.NIData.mId + " = ? " , new String[]{mId});
        return 0;
    }

    private static ContentValues getContentValues (NewsInfo newsinfo) {
        ContentValues values = new ContentValues();
        values.put(NewsInfoTable.NIData.mId,newsinfo.getId().toString());
        values.put(NewsInfoTable.NIData.mTitle,newsinfo.getTitle());
        values.put(NewsInfoTable.NIData.mDate,newsinfo.getDate().getTime());
        values.put(NewsInfoTable.NIData.mStat,newsinfo.isRead() ? 1:0);

        return values;
    }

}
