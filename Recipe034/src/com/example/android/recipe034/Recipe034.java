package com.example.android.recipe034;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Recipe034 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView textView = (TextView)findViewById(R.id.TextView01);
        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this)
        .setTitle("Title")
        .setMessage("Enter your name")
        .setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String text = "Hello ";
                text += editText.getText();
                text += " !";
                textView.setText(text);
            }
        })
        // EditTextをセットする
        .setView(editText)
        .create()
        .show();
    }
}
