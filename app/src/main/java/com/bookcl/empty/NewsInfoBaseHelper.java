package com.bookcl.empty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bookcl.empty.NewsInfoDBSchema.NewsInfoTable;

/**
 * Created by lijin on 2017/7/3.
 */

public class NewsInfoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "NewsInfoDB";
    private final String TAG = "[JamesL]-NIBaseHelper";

    public NewsInfoBaseHelper (Context context) {
        super(context,DB_NAME,null,VERSION);
        Log.i(TAG,"NewsInfoBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"onCreate");
        db.execSQL("create table " + NewsInfoTable.NI_NAME + " ( " +
                " _id integer primary key autoincrement, " +
                NewsInfoTable.NIData.mId + ", " +
                NewsInfoTable.NIData.mTitle + ", " +
                NewsInfoTable.NIData.mDate + ", " +
                NewsInfoTable.NIData.mStat +
                " ) "
        );
        Log.i(TAG,"Create DB OK!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
