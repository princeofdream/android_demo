package cn.bookcl.nfc_logger.tagdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by JamesL on 2021/04/19.
 */

public class TagDatabaseLab {
    private static TagDatabaseLab sTagDatabaseLab;

    private static final String TAG = "[JamesL]-TagDatabaseLab";

    private static List<TagDatabaseStruct> mTagDatabaseStructList;
    private static final int NEW_EVENT = 10;
    private Context mCtx;
    private SQLiteDatabase mDB;

    public static TagDatabaseLab get(Context mContext) {
        if(sTagDatabaseLab == null) {
            Log.i(TAG,"Static TagDatabaseLab is NULL!!");
            sTagDatabaseLab = new TagDatabaseLab(mContext);
        } else {
            Log.i(TAG,"get NewsInoLab!");
        }
        return sTagDatabaseLab;
    }

    public TagDatabaseLab(Context mContext) {
        // mTagDatabaseStructList = new ArrayList<>();

        /* init DB */
        mCtx = mContext.getApplicationContext();
        mDB = new TagDatabaseHelper(mCtx).getWritableDatabase();
        Log.i(TAG,"TagDatabaseLab");

        /** We have insert db by manual **/

        /*
        for(int i0=0; i0 < NEW_EVENT; i0++) {
            TagDatabaseStruct TagDatabaseStruct = new TagDatabaseStruct();
            TagDatabaseStruct.setVar_payload("News # " + i0);
            Random rmd = new Random();
            TagDatabaseStruct.setVar_count(i0);

            Date mdate = new Date();
            //mdate.setYear(2015+rmd.nextInt(3));
            mdate.setMonth(rmd.nextInt(12));
            mdate.setDate(1+rmd.nextInt(28));
            TagDatabaseStruct.setVar_date(mdate);

            AddTagDatabaseStruct(TagDatabaseStruct);
        }
        */
        getTagDatabaseStructList();

    }

    public List<TagDatabaseStruct> getTagDatabaseStructList(){
        Log.i(TAG,"getTagDatabaseStructList");
        mTagDatabaseStructList = null;
        mTagDatabaseStructList = new ArrayList<>();
        TagDatabaseCursorWrapper cursor = queryTagDatabaseStruct(null,null);
        try {
            cursor.moveToFirst();
            Log.i(TAG,"Start List <" + cursor.getCount() + "> -->");

            if (cursor.getCount() == 0 ) {
                Log.i(TAG,"cursor count is 0");
                return mTagDatabaseStructList;
            }

            while(!cursor.isAfterLast()){
                TagDatabaseStruct mTagDatabaseStruct = cursor.getTagDatabaseStructbyDB();
                Log.i(TAG, "" + mTagDatabaseStruct.getVar_payload());
                mTagDatabaseStructList.add(mTagDatabaseStruct);
                cursor.moveToNext();
            }
        } finally {
            Log.i(TAG,"<-- End of List");
            cursor.close();
        }
        Log.i(TAG,"mTagDatabaseStructList size: " + mTagDatabaseStructList.size());
        return mTagDatabaseStructList;
    }

    public void clearTagDatabaseStructList(){
        Log.i(TAG,"getTagDatabaseStructList");
        mTagDatabaseStructList = null;
        mTagDatabaseStructList = new ArrayList<>();
        TagDatabaseCursorWrapper cursor = queryTagDatabaseStruct(null,null);
        try {
            cursor.moveToFirst();
            Log.i(TAG,"Start List <" + cursor.getCount() + "> -->");

            if (cursor.getCount() == 0 ) {
                Log.i(TAG,"cursor count is 0");
                return;
            }

            while(!cursor.isAfterLast()){
                TagDatabaseStruct mTagDatabaseStruct = cursor.getTagDatabaseStructbyDB();
                Log.i(TAG, "" + mTagDatabaseStruct.getVar_payload());
                mTagDatabaseStructList.add(mTagDatabaseStruct);
                cursor.moveToNext();
            }
        } finally {
            Log.i(TAG,"<-- End of List");
            cursor.close();
        }
        Log.i(TAG,"mTagDatabaseStructList size: " + mTagDatabaseStructList.size());

    }

    public TagDatabaseStruct getTagDatabaseStruct(UUID mId) {
        /*
        for (TagDatabaseStruct gTagDatabaseStruct : mTagDatabaseStructList) {
            if (gTagDatabaseStruct.getId().equals(mId)) {
                return gTagDatabaseStruct;
            }
        }
        return null;
        */
        Log.i(TAG,"getTagDatabaseStruct");
        TagDatabaseStruct mTagDatabaseStruct = null;
        TagDatabaseCursorWrapper cursor = queryTagDatabaseStruct(TagDatabaseTable.NIData.mId + " = ? ",
                new String[]{mId.toString()});

        try {
            if (cursor.getCount() == 0 ) {
                Log.i(TAG,"cursor count is 0");
                return null;
            }

            cursor.moveToFirst();
            mTagDatabaseStruct = cursor.getTagDatabaseStructbyDB();
        }finally {
            cursor.close();
        }

        return mTagDatabaseStruct;
    }

    public int AddTagDatabaseStruct(TagDatabaseStruct TagDatabaseStruct) {
        Log.i(TAG,"AddTagDatabaseStruct");
        final String db_name;
        db_name = TagDatabaseTable.NI_NAME;

        if(TagDatabaseStruct == null)
            return -1;

        if (TagDatabaseStruct.getVar_payload() == null || TagDatabaseStruct.getVar_payload().length() == 0)
            TagDatabaseStruct.setVar_payload("[null] # " + mTagDatabaseStructList.size());
        //mTagDatabaseStructList.add(TagDatabaseStruct);

        ContentValues values = getContentValues(TagDatabaseStruct);
        mDB.insert(db_name,null,values);
        return 0;
    }

    public int DeleteTagDatabaseStruct(TagDatabaseStruct TagDatabaseStruct) {
        Log.i(TAG,"AddTagDatabaseStruct");

        if(TagDatabaseStruct == null)
            return -1;

        ContentValues values = getContentValues(TagDatabaseStruct);
        mDB.delete(TagDatabaseTable.NI_NAME,TagDatabaseTable.NIData.mId + " =? ", new String[] {TagDatabaseStruct.getVar_id().toString()});

        return 0;
    }

    public int UpdateTagDatabaseStruct(TagDatabaseStruct TagDatabaseStruct) {
        String mId = TagDatabaseStruct.getVar_id().toString();
        ContentValues values = getContentValues(TagDatabaseStruct);

        mDB.update(TagDatabaseTable.NI_NAME,values,TagDatabaseTable.NIData.mId + " = ? " , new String[]{mId});
        return 0;
    }

    public TagDatabaseCursorWrapper queryTagDatabaseStruct(String whereClause, String[] whereArgs ) {
        Log.i(TAG,"queryTagDatabaseStruct");
        Cursor mcursor = mDB.query(TagDatabaseTable.NI_NAME,
                null,   //columns, null to select all columns
                whereClause,
                whereArgs,
                null,   //group by
                null,   //having
                null    //order by
        );
        return new TagDatabaseCursorWrapper(mcursor);
    }


    private static ContentValues getContentValues (TagDatabaseStruct TagDatabaseStruct) {
        ContentValues values = new ContentValues();
        values.put(TagDatabaseTable.NIData.mId,TagDatabaseStruct.getVar_id().toString());
        values.put(TagDatabaseTable.NIData.mPayload,TagDatabaseStruct.getVar_payload());
        values.put(TagDatabaseTable.NIData.mDate,TagDatabaseStruct.getVar_date().getTime());
        values.put(TagDatabaseTable.NIData.mCount,TagDatabaseStruct.getVar_count());

        return values;
    }


}
