package com.bookcl.empty;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Random;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UUID = "newsinfo-uuid";

    private UUID muuid;

    private OnFragmentInteractionListener mListener;

    private static final String TAG="[JamesL]-NewsFrag";

    private NewsInfo mNewsInfo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mReadCheckbox;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance(UUID uuid) {
        NewsFragment fragment = new NewsFragment();
        if(uuid != null) {
            Bundle args = new Bundle();
            args.putString(ARG_UUID, uuid.toString());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.i(TAG,"get UUID:"+getArguments().getString(ARG_UUID));
            muuid = UUID.fromString(getArguments().getString(ARG_UUID));
        } else {
            muuid = null;
            Log.i(TAG,"do not get an argument!");
        }

        NewsInfoLab mNewsInfoLab = NewsInfoLab.get(getActivity());
        if(mNewsInfoLab != null && muuid != null) {
            Log.i(TAG,"get newsinfo from uuid"+muuid.toString());
            mNewsInfo = mNewsInfoLab.getNewsInfo(muuid);
            Log.i(TAG,"get info id" + mNewsInfo.getId());
        } else {
            Log.i(TAG,"Something is Wrong[null]!!");
        }

        if(mNewsInfo == null) {
            Log.i(TAG,"NewsInfo is NULL!!");
            mNewsInfo = new NewsInfo();
        }
        else {
            Log.i(TAG,"get NewsInfo Success!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag_view = inflater.inflate(R.layout.fragment_news, container, false);

        mTitleField = (EditText) frag_view.findViewById(R.id.news_title_field);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewsInfo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)frag_view.findViewById(R.id.news_info_btn);
        mDateButton.setText(mNewsInfo.getDate().toString());
        mDateButton.setEnabled(false);

        mReadCheckbox = (CheckBox) frag_view.findViewById(R.id.news_checkbox_stat);
        mReadCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNewsInfo.setRead(isChecked);
            }
        });


        mTitleField.setText(mNewsInfo.getTitle());
        mReadCheckbox.setChecked(mNewsInfo.isRead());

        return frag_view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
