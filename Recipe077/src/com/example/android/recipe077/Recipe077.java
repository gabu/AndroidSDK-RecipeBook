package com.example.android.recipe077;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class Recipe077 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView text1 = (TextView)findViewById(R.id.text1);

        ConnectivityManager cm;
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info == null) {
            text1.setText("オフライン");
        } else {
            if (info.isConnected()) {
                text1.setText("オンライン");
            } else {
                text1.setText("オフライン");
            }
        }
    }
}