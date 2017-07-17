package com.bookcl.empty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        // mNewsInfoList = new ArrayList<>();

        /* init DB */
        mCtx = mContext.getApplicationContext();
        mDB = new NewsInfoBaseHelper(mCtx).getWritableDatabase();
        Log.i(TAG,"NewsInfoLab");

        /** We have insert db by manual **/

        /*
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
        */

    }

    public List<NewsInfo> getNewsInfoList(){
        Log.i(TAG,"getNewsInfoList");
        mNewsInfoList = null;
        mNewsInfoList = new ArrayList<>();
        NewsInfoCursorWrapper cursor = queryNewsInfo(null,null);
        try {
            cursor.moveToFirst();
            Log.i(TAG,"Start List <" + cursor.getCount() + "> -->");

            if (cursor.getCount() == 0 ) {
                Log.i(TAG,"cursor count is 0");
                return mNewsInfoList;
            }

            while(!cursor.isAfterLast()){

                NewsInfo mNewsInfo = cursor.getNewsInfobyDB();
                Log.i(TAG, "" + mNewsInfo.getTitle());
                mNewsInfoList.add(mNewsInfo);
                cursor.moveToNext();
            }
        } finally {
            Log.i(TAG,"<-- End of List");
            cursor.close();
        }
        Log.i(TAG,"mNewsInfoList size: " + mNewsInfoList.size());
        return mNewsInfoList;
    }

    public NewsInfo getNewsInfo(UUID mId) {
        /*
        for (NewsInfo gNewsInfo : mNewsInfoList) {
            if (gNewsInfo.getId().equals(mId)) {
                return gNewsInfo;
            }
        }
        return null;
        */
        Log.i(TAG,"getNewsInfo");
        NewsInfo mNewsInfo = null;
        NewsInfoCursorWrapper cursor = queryNewsInfo(NewsInfoTable.NIData.mId + " = ? ",
                new String[]{mId.toString()});

        try {
            if (cursor.getCount() == 0 ) {
                Log.i(TAG,"cursor count is 0");
                return null;
            }

            cursor.moveToFirst();
            mNewsInfo = cursor.getNewsInfobyDB();
        }finally {
            cursor.close();
        }

        return mNewsInfo;
    }

    public int AddNewsInfo(NewsInfo newsinfo) {
        Log.i(TAG,"AddNewsInfo");

        if(newsinfo == null)
            return -1;

        if (newsinfo.getTitle() == null || newsinfo.getTitle().length() == 0)
            newsinfo.setTitle("[null] News # " + mNewsInfoList.size());
        mNewsInfoList.add(newsinfo);
        
        ContentValues values = getContentValues(newsinfo);
        mDB.insert(NewsInfoTable.NI_NAME,null,values);
        return 0;
    }

    public int DeleteNewsInfo(NewsInfo newsinfo) {
        Log.i(TAG,"AddNewsInfo");

        if(newsinfo == null)
            return -1;

        ContentValues values = getContentValues(newsinfo);
        mDB.delete(NewsInfoTable.NI_NAME,NewsInfoTable.NIData.mId + " =? ", new String[] {newsinfo.getId().toString()});

        return 0;
    }

    public int UpdateNewsInfo(NewsInfo newsinfo) {
        String mId = newsinfo.getId().toString();
        ContentValues values = getContentValues(newsinfo);

        mDB.update(NewsInfoTable.NI_NAME,values,NewsInfoTable.NIData.mId + " = ? " , new String[]{mId});
        return 0;
    }

    public NewsInfoCursorWrapper queryNewsInfo(String whereClause, String[] whereArgs ) {
        Log.i(TAG,"queryNewsInfo");
        Cursor mcursor = mDB.query(NewsInfoTable.NI_NAME,
                null,   //columns, null to select all columns
                whereClause,
                whereArgs,
                null,   //group by
                null,   //having
                null    //order by
                );
        return new NewsInfoCursorWrapper(mcursor);
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
