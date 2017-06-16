package com.bookcl.empty;

import java.util.UUID;

/**
 * Created by lijin on 2017/6/16.
 */

public class NewsInfo {
    private UUID mId;
    private String mTitle;

    public NewsInfo() {
        //gen unique id
        mId = UUID.randomUUID();
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
}
