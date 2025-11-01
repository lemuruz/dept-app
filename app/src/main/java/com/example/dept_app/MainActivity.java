package com.example.dept_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.dept_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'dept_app' library on application startup.
    static {
        System.loadLibrary("dept_app");
//        System.loadLibrary("native-lib");
    }
    public native int calculateNetDebt(int youOwe, int friendOwes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.sample_text);
        int youOwe = 50;
        int friendOwes = 120;


        int netDebt = calculateNetDebt(youOwe, friendOwes);

        text.setText("Net debt = " + netDebt);
    }

    /**
     * A native method that is implemented by the 'dept_app' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}