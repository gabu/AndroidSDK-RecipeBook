package com.example.android.recipe019;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class Recipe019 extends Activity {

    private static final String TAG = "Recipe019";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button1:
            Intent intent = new Intent(this, MyPreferenceActivity.class);
            startActivity(intent);
            break;
        case R.id.button2:
            String key = getResources().getString(R.string.real_password_key);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            String password = sp.getString(key, "no input");
            Log.d(TAG, "password=" + password);
            break;
        }
    }
}
