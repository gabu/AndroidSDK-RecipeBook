package com.example.android.recipe011;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class Recipe011 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}