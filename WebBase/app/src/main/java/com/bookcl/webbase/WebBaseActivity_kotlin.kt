package com.bookcl.webbase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import kotlinx.android.synthetic.main.activity_web_base.*

class WebBaseActivity_kotlin : AppCompatActivity() {

    private val TAG = "SnepClient"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                sample_text.setText(R.string.title_home)
                Log.i(TAG,"nav to home");
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                Log.i(TAG,"nav to dashboard");
                sample_text.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Log.i(TAG,"nav to notification");
                sample_text.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_base)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //WebBaseHomeFragment mWebBaseHomeFragment;



    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
