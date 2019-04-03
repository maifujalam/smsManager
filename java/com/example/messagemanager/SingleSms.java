package com.example.messagemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SingleSms extends AppCompatActivity {
    public static final String TAG="Single SMS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sms);
        Log.d(TAG, "onCreate: ");
    }
}
