package com.example.android.recipe001;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;

public class Recipe001 extends Activity {

    private static final String TAG = "Recipe001";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String androidId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        if (androidId == null) {
            // Running on emulator
            Log.d(TAG, "Running on emulator");
        } else {
            // Running on real device
            Log.d(TAG, "Running on real device");
        }
    }
}