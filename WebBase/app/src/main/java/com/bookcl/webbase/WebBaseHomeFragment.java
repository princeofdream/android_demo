package com.bookcl.webbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesL on 2018/2/12.
 */

public class WebBaseHomeFragment extends Fragment {

    private static WebBaseHomeRecyclerViewAdapter mWebBaseHomeRecyclerViewAdapter;
    public static RecyclerView recyclerView;
    private static final String TAG="WebBaseHomeFragment";

    public static WebBaseHomeFragment newInstance() {

        Bundle args = new Bundle();

        WebBaseHomeFragment fragment = new WebBaseHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG,"onCreateView");

        View itemview = inflater.inflate(R.layout.fragment_web_base_home, container,false);

        List<String> mValue = new ArrayList<String>();
        mValue.add("byJamesL 0001");
        mValue.add("byJamesL 0002");
        mValue.add("byJamesL 0003");
        mValue.add("byJamesL 0004");
        mValue.add("byJamesL 0005");
        mValue.add("byJamesL 0006");
        mValue.add("byJamesL 0007");
        mValue.add("byJamesL 0008");
        mValue.add("byJamesL 0009");
        mValue.add("byJamesL 00010");
        mValue.add("byJamesL 00011");
        mValue.add("byJamesL 0012");
        mValue.add("byJamesL 0013");
        
        if (itemview instanceof RecyclerView){
            Context context = itemview.getContext();
            recyclerView = (RecyclerView) itemview;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        mWebBaseHomeRecyclerViewAdapter = new WebBaseHomeRecyclerViewAdapter(mValue);
        recyclerView.setAdapter(mWebBaseHomeRecyclerViewAdapter);

        return itemview;
    }
}
