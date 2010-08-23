package com.example.android.recipe013;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Recipe013 extends Activity {

    private static final String TAG = "Recipe013";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<Integer> list = new ArrayList<Integer>();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));

        // シャッフル！
        Collections.shuffle(list);

        // ログ出力
        for (Integer i : list) {
            Log.d(TAG, i.toString());
        }
    }
}