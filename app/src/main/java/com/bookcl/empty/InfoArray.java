package com.bookcl.empty;

/**
 * Created by lijin on 2017/6/6.
 */

public class InfoArray {
    private int mTextRes;
    private boolean mResult;

    public InfoArray(int InfoTextRes, boolean Ret) {
        mTextRes = InfoTextRes;
        mResult = Ret;
    }

    public int getInfo() {
        return mTextRes;
    }

    public void setInfo(int InfoTextRes ) {
        mTextRes = InfoTextRes;
        return;
    }

    public boolean getResult() {
        return mResult;
    }

    public void setResult(boolean Ret) {
        mResult = Ret;
        return;
    }

}
