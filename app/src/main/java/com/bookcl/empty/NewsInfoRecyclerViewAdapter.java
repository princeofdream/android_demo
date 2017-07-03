package com.bookcl.empty;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bookcl.empty.NewsInfoFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.UUID;


public class NewsInfoRecyclerViewAdapter extends RecyclerView.Adapter<NewsInfoRecyclerViewAdapter.ViewHolder> {

    private List<NewsInfo> mValues;
    private final OnListFragmentInteractionListener mListener;
    private int mAction;

    private static final String TAG = "[JamesL]-NsInRcyAdp";

    public NewsInfoRecyclerViewAdapter(List<NewsInfo> items, OnListFragmentInteractionListener listener, int action) {
        mValues = items;
        mListener = listener;
        mAction = action;

        Log.i(TAG,"Start RecyclerView in action " + mAction);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mReadStat.setChecked(mValues.get(position).isRead());
        holder.mContentView.setText(mValues.get(position).getTitle());
        holder.mBtn.setText("Btn " + position);

        /*
        if(mAction < 1)
            holder.mReadStat.setVisibility(View.INVISIBLE);
        else
            holder.mReadStat.setVisibility(View.VISIBLE);
        */

        holder.mReadStat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.mItem.setRead(isChecked);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAction++;
                mListener.LongPressAction(holder.mItem, mAction);
                return false;
            }
        });

        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicking btn"+ holder.mBtn.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewsInfoList(List<NewsInfo> newsinfo) {
        Log.i(TAG,"setNewsInfoList");
        mValues = newsinfo;
    }

    public int getPosition(UUID uuid) {
        Log.i(TAG,"get list size: "+ mValues.size());
        int ret = 0;
        for(int i0=0;i0<mValues.size();i0++) {
            if ( mValues.get(i0).getId().equals(uuid)) {
                ret = i0;
                break;
            }
        }
        return ret;
    }

    public class ViewHolder extends RecyclerView.ViewHolder /* implements View.OnClickListener */ {
        public View mView;
        public CheckBox mReadStat;
        public TextView mContentView;
        public Button mBtn;
        public NewsInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mReadStat = (CheckBox) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mBtn = (Button) view.findViewById(R.id.rcy_btn);
            // we have setup click event in onBindViewHolder
            //mContentView.setOnClickListener(this);
            //view.setOnClickListener(this);
        }

        /*
        @Override
        public void onClick(View view) {
            Log.i(TAG,"Clicking by James!");
        }
        */

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
