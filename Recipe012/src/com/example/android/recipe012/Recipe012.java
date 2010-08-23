package com.example.android.recipe012;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Recipe012 extends Activity {

    private static final String TAG = "Recipe012";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String s;
        s = URLEncoder.encode("おはよう");
        Log.d(TAG, "UTF-8(default)=" + s);
        try {
            s = URLEncoder.encode("おはよう", "EUC-JP");
            Log.d(TAG, "EUC-JP=" + s);
            s = URLEncoder.encode("おはよう", "SJIS");
            Log.d(TAG, "SJIS=" + s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}