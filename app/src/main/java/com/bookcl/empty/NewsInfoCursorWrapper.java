package com.bookcl.empty;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bookcl.empty.NewsInfoDBSchema.NewsInfoTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lijin on 2017/7/3.
 */

public class NewsInfoCursorWrapper extends CursorWrapper {

    public NewsInfoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public NewsInfo getNewsInfobyDB() {
        String mId = getString(getColumnIndex(NewsInfoTable.NIData.mId));
        String mTitle = getString(getColumnIndex(NewsInfoTable.NIData.mTitle));
        long mDate = getLong(getColumnIndex(NewsInfoTable.NIData.mDate));
        int mStat = getInt(getColumnIndex(NewsInfoTable.NIData.mStat));

        if(mTitle == null) {
            mTitle = "null";
        }

        NewsInfo mNewsInfo = new NewsInfo(UUID.fromString(mId));
        mNewsInfo.setTitle(mTitle);
        mNewsInfo.setDate(new Date(mDate));
        mNewsInfo.setRead(mStat != 0);


        return mNewsInfo;
    }
}
