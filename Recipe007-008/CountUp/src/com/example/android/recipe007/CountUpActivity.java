package com.example.android.recipe007;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CountUpActivity extends Activity {

    private TextView mCountView;
    private int mCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mCountView = (TextView)findViewById(R.id.TextView01);
    }

    public void onClick(View v) {
        mCountView.setText(Integer.toString(++mCount));
    }

    public int plus(int a, int b) {
        return a + b;
    }
}
