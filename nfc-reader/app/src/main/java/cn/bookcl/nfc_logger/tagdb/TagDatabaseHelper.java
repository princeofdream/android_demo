package cn.bookcl.nfc_logger.tagdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class TagDatabaseHelper extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String DB_NAME = "TagHistory";
    private final String TAG = "james_nfc_log_db";

    public TagDatabaseHelper (Context context) {
        super(context,DB_NAME,null,VERSION);
        Log.i(TAG,"NewsInfoBaseHelper");
    }

    public TagDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"onCreate");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TagDatabaseTable.NI_NAME + " ( " +
                " _id integer primary key autoincrement, " +
                TagDatabaseTable.NIData.mId + ", " +
                TagDatabaseTable.NIData.mPayload + ", " +
                TagDatabaseTable.NIData.mDate+ ", " +
                TagDatabaseTable.NIData.mCount +
                " ) "
        );
        Log.i(TAG,"Create DB OK!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
