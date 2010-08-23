package com.example.android.recipe021;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Recipe021 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = new Intent(this, MyPreferenceActivity.class);
        startActivity(intent);
    }
}