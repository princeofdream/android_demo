package com.bookcl.empty;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lijin on 2017/6/16.
 */

public class NewsInfo {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mRead;

    public NewsInfo() {
        //gen unique id
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId(){
        return mId;
    }

    public String getTitle(){
        return mTitle;
    }

    public int setTitle(String title){
        mTitle = title;
        return 0;
    }

    public Date getDate(){
        return mDate;
    }

    public void setDate(Date date){
        mDate = date;
    }

    public boolean isRead(){
        return mRead;
    }

    public void setRead(boolean readed){
        mRead = readed;
    }

}
