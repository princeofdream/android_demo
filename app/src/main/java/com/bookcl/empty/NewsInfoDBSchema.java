package com.bookcl.empty;

/**
 * Created by lijin on 2017/7/3.
 */

public class NewsInfoDBSchema {
    public static class NewsInfoTable {
        public static final String NI_NAME = "NewsInfoTable";
        public static class NIData {
            public static final String mId = "uuid";
            public static final String mTitle = "title";
            public static final String mDate = "date";
            public static final String mStat = "stat";
        }
    }
}
