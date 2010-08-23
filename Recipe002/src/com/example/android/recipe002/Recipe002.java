package com.example.android.recipe002;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class Recipe002 extends Activity {

    private static final String TAG = "Recipe002";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d(TAG, "DEVICE=" + Build.DEVICE);
        Log.d(TAG, "MODEL=" + Build.MODEL);
        Log.d(TAG, "CPU_ABI=" + Build.CPU_ABI);
        Log.d(TAG, "PRODUCT=" + Build.PRODUCT);

        Log.d(TAG, "CODENAME=" + Build.VERSION.CODENAME);
        Log.d(TAG, "INCREMENTAL=" + Build.VERSION.INCREMENTAL);
        Log.d(TAG, "RELEASE=" + Build.VERSION.RELEASE);
        Log.d(TAG, "SDK=" + Build.VERSION.SDK);
        Log.d(TAG, "SDK_INT=" + Build.VERSION.SDK_INT);

        switch (Build.VERSION.SDK_INT) {
        case Build.VERSION_CODES.BASE:
            // 1.0
            break;
        case Build.VERSION_CODES.BASE_1_1:
            // 1.1
            break;
        case Build.VERSION_CODES.CUPCAKE:
            // 1.5
            break;
        case Build.VERSION_CODES.DONUT:
            // 1.6
            break;
//        case Build.VERSION_CODES.ECLAIR:
//            // 2.0
//            break;
//        case Build.VERSION_CODES.ECLAIR_0_1:
//            // 2.0.1
//            break;
//        case Build.VERSION_CODES.ECLAIR_MR1:
//            // 2.1
//            break;
        default:
            break;
        }
    }
}