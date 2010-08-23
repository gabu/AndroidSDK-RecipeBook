package com.example.android.recipe030;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Recipe030 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linerLayout = new LinearLayout(this);
        linerLayout.setBackgroundColor(Color.GRAY);
        linerLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i=0; i<8; i++) {
            LinearLayout childLinerLayout = new LinearLayout(this);
            for (int j=0; j<8; j++) {
                ImageView imageView = new ImageView(this);
                if ((j + i) % 2 == 0) {
                    imageView.setImageResource(R.drawable.black);
                } else {
                    imageView.setImageResource(R.drawable.white);
                }
                childLinerLayout.addView(imageView);
            }
            linerLayout.addView(childLinerLayout);
        }

        setContentView(linerLayout);
    }
}
