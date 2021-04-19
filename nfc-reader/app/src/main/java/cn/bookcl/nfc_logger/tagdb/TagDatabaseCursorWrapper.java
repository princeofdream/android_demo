package cn.bookcl.nfc_logger.tagdb;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;


public class TagDatabaseCursorWrapper extends CursorWrapper {

    public TagDatabaseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public TagDatabaseStruct getTagDatabaseStructbyDB() {
        String var_id = getString(getColumnIndex(TagDatabaseTable.NIData.mId));
        String var_payload = getString(getColumnIndex(TagDatabaseTable.NIData.mPayload));
        long var_date = getLong(getColumnIndex(TagDatabaseTable.NIData.mDate));
        int var_count = getInt(getColumnIndex(TagDatabaseTable.NIData.mCount));

        TagDatabaseStruct mTagDatabaseStruct = new TagDatabaseStruct(UUID.fromString(var_id));
        mTagDatabaseStruct.setVar_payload(var_payload);
        mTagDatabaseStruct.setVar_date(new Date(var_date));
        mTagDatabaseStruct.setVar_count(var_count);

        return mTagDatabaseStruct;
    }
}
