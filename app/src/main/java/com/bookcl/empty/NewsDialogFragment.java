package com.bookcl.empty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDialogFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEWS_DIALOG_DATE = "new_dialog_date";
    public static final String EXTRA_DATE = "com.bookcl.android.newsdialog.date";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "[JamesL]-NewsDlgFrgm";

    private Date mDate;
    private String mParam2;
    private DatePicker mDatePicker;

    private static final boolean USE_XML_LAYOUT = false;

    private OnFragmentInteractionListener mListener;

    public NewsDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param date Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsDialogFragment.
     */
    public static NewsDialogFragment newInstance(Date date, String param2) {
        NewsDialogFragment fragment = new NewsDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_DIALOG_DATE, date);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("Title by JamesL");
        mDatePicker = new DatePicker(getActivity());
        //mDataPicker.setId(View.generateViewId());
        mDatePicker.setId(R.id.CUSTOM_ID_000);

        mBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int myear = mDatePicker.getYear();
                int mmon = mDatePicker.getMonth();
                int mday = mDatePicker.getDayOfMonth();
                Log.i(TAG,"get date by picker:" +myear + "-" + mmon+"-"+mday);
                Date date = new GregorianCalendar(myear, mmon, mday).getTime();
                sendResult(Activity.RESULT_OK, date);
            }
        });

        mDate = (Date)getArguments().getSerializable(ARG_NEWS_DIALOG_DATE);
        Calendar mcal = Calendar.getInstance();
        mcal.setTime(mDate);
        int myear = mcal.get(Calendar.YEAR);
        int mmon = mcal.get(Calendar.MONTH);
        int mday = mcal.get(Calendar.DAY_OF_MONTH);

        Log.i(TAG,"get date:" +myear + "-" + mmon+"-"+mday);
        mDatePicker.init(myear,mmon,mday,null);
        mBuilder.setView(mDatePicker);

        return mBuilder.create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = (Date)getArguments().getSerializable(ARG_NEWS_DIALOG_DATE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_dialog, container, false);
    }

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
   */

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
        void onFragmentInteraction(Uri uri);
    }
}
