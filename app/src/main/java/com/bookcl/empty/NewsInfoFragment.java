package com.bookcl.empty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsInfoFragment extends Fragment {

    private static final String TAG="[JamesL]-NewsInfoFrag";

    private static final String ARG_COLUMN_COUNT = "newsinfo-column";
    private static final String ARG_ACTION = "newsinfo-action";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mAction = 0;
    private OnListFragmentInteractionListener mListener;

    public static NewsInfoLab mNewsInfoLab;
    public static RecyclerView recyclerView;
    public static NewsInfoRecyclerViewAdapter mNewsInfoRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsInfoFragment() {
    }

    @SuppressWarnings("unused")
    public static NewsInfoFragment newInstance(int columnCount,int action) {
        NewsInfoFragment fragment = new NewsInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mAction = getArguments().getInt(ARG_ACTION);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsinfo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            mNewsInfoLab = NewsInfoLab.get(getActivity());
            List<NewsInfo> getNewsInfo = mNewsInfoLab.getNewsInfoList();

            mNewsInfoRecyclerViewAdapter = new NewsInfoRecyclerViewAdapter(getNewsInfo, mListener,mAction);
            recyclerView.setAdapter(mNewsInfoRecyclerViewAdapter);

        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static void updateUI(UUID uuid) {
        int position = 0;
        position = mNewsInfoRecyclerViewAdapter.getPosition(uuid);
        recyclerView.getAdapter().notifyItemChanged(position);
    }

    public static void updateFullUI() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(NewsInfo item);
        void LongPressAction(NewsInfo info, int index);
        void onMenuAddItem(NewsInfo newsinfo);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_news_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_news_list) {
            NewsInfo newsinfo = new NewsInfo();
            newsinfo.setDate(new Date());
            newsinfo.setTitle(null);
            newsinfo.setRead(false);
            mNewsInfoLab.AddNewsInfo(newsinfo);
            Log.i(TAG,"Add newsinfo");
            mListener.onMenuAddItem(newsinfo);
        } else {
            Log.i(TAG,"not select menu Add icon");
        }
        return super.onOptionsItemSelected(item);
    }
}
