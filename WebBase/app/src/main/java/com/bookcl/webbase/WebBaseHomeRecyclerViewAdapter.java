package com.bookcl.webbase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JamesL on 2018/2/12.
 */

public class WebBaseHomeRecyclerViewAdapter extends RecyclerView.Adapter<WebBaseHomeRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mValue;

    public WebBaseHomeRecyclerViewAdapter(List<String> items){
        mValue = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_web_base_home_adpter, parent,false);
        mContext = parent.getContext();
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextVal.setText(mValue.get(position));
    }

    @Override
    public int getItemCount() {
        return mValue.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextVal;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextVal = (TextView) itemView.findViewById(R.id.fragment_web_base_home_adapter_text);
        }
    }
}
