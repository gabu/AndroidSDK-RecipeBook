package com.example.adnroid.recipe009;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Recipe009 extends Activity {

    private static final String TAG = "Recipe009";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String filename = System.currentTimeMillis() + ".txt";
        Log.d(TAG, filename);

        Log.d(TAG, getUniqueFileName(".txt"));
        Log.d(TAG, getUniqueFileName(".txt"));
        Log.d(TAG, getUniqueFileName(".txt"));
    }

    private String getUniqueFileName(String ext) {
        return java.util.UUID.randomUUID() + ext;
    }
}