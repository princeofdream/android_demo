package abc.bookcl.test01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import abc.bookcl.test01.TSUnit.UnitMain;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    public static native String getStringFormC();

    static {
        System.loadLibrary("xyz");    //defaultConfig.ndk.moduleName
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TAG", "--------------------> " + getStringFormC());
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(getStringFormC());
        /*
        Log.d("TAG", "--------------------> " + UnitMain.get_java_str("=="));
        Log.d("TAG","--------------------> " + UnitMain.getStringFormC());
        textView = (TextView) findViewById(R.id.textView);
        //textView.setText(UnitMain.getStringFormC());
        textView.setText(UnitMain.get_java_str(null) + UnitMain.getStringFormC());
        */
    }
}
