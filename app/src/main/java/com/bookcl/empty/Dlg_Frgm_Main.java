package com.bookcl.empty;

import android.app.DialogFragment ;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by lijin on 2017/6/2.
 */

public class Dlg_Frgm_Main extends DialogFragment {

    int mNum;

    public static Dlg_Frgm_Main newInstance(int num){
        Dlg_Frgm_Main mdlg = new Dlg_Frgm_Main();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        mdlg.setArguments(args);

        return mdlg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        Log.d("JamesL","get number -->"+mNum);

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 0: style = DialogFragment.STYLE_NORMAL; break;
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            //case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 0: theme = 0; break;
            case 1: theme = android.R.style.Theme_Holo; break;
            case 2: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 3: theme = android.R.style.Theme_Holo_Light; break;
            case 4: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 5: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frgm_dlg_main_ly, container, false);
        TextView tv = (TextView)v.findViewById(R.id.dlg_info);
        tv.setTextColor(0xFFFF00FF);
        tv.setText("Dialog #" + (mNum-1)%6 + ": using style ");


        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                Log.d("JamesL","------> 006, get num" + mNum);
            }
        });

        return v;
    }

}
