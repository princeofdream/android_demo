package com.bookcl.empty;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowtimeActivity extends AppCompatActivity {

    private static final String TAG = "[JamesL]-St";
    public static final String KEY_SHOWTIME_ACT_EXT = "com.bookcl.empty.info_count";
    public static final String KEY_SHOWTIME_ACT_EXT_SHOW = "com.bookcl.empty.info_show";
    private static int get_info_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_showtime);
        get_info_count = getIntent().getIntExtra(KEY_SHOWTIME_ACT_EXT, 0);
        Log.i(TAG,"Get info count: " + get_info_count);
        TextView mInfo = (TextView)findViewById(R.id.txinfo);
        mInfo.setText("Count: "+get_info_count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(ShowtimeActivity.this,"Get info Count: " + get_info_count,Toast.LENGTH_LONG).show();
        //setResult(Activity.RESULT_FIRST_USER);

        final Button mPrev = (Button) findViewById(R.id.show_prev);
        mPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Click show prev button!");
                setShowtimeResult(100);

                int cx = mPrev.getWidth()/2;
                int cy = mPrev.getHeight()/2;
                float radius = mPrev.getWidth();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator anim = ViewAnimationUtils.createCircularReveal(mPrev, cx, cy, radius, 0);
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mPrev.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    anim.start();
                }
                else
                    mPrev.setVisibility(View.INVISIBLE);
            }
        });

        Button mNext = (Button) findViewById(R.id.show_next);
        mNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Click show next button!");
                setShowtimeResult(101);
                mPrev.setVisibility(View.VISIBLE);
            }
        });

    }

    public static Intent newIntent(Context packageContext, int mcount) {
        Intent mshowtime_int = new Intent(packageContext,ShowtimeActivity.class);
        mshowtime_int.putExtra(KEY_SHOWTIME_ACT_EXT,mcount);
        Log.i(TAG,"newIntent");
        return mshowtime_int;
    }

    private void setShowtimeResult(int ret)
    {
        Intent data = new Intent();
        data.putExtra(KEY_SHOWTIME_ACT_EXT_SHOW,ret);
        setResult(RESULT_OK,data);
    }
}
