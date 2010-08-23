package com.example.android.recipe014;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;

public class Recipe014 extends Activity {

    private static final String TAG = "Recipe014";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String text;
        ClipboardManager clipboard =
            (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (clipboard.hasText()) {
            text = clipboard.getText().toString();
            Log.d(TAG, text);
        }

        text = "sets by application!";
        clipboard.setText(text);
    }
}