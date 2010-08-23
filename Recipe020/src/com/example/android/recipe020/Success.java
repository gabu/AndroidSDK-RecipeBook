package com.example.android.recipe020;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Success extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, MyPreferenceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() == false) finish();
    }
}
