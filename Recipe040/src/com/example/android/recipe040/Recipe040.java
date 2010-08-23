package com.example.android.recipe040;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Recipe040 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 文字列のみのシンプルなAdapterを作成
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                      android.R.layout.simple_list_item_1);
        // 0から19までのリストアイテムを追加
        for (int i=0; i<20; i++) {
            adapter.add("item_" + i);
        }
        ListView listView = (ListView) findViewById(R.id.ListView01);
        listView.setAdapter(adapter);
        // 表示位置を指定
        listView.setSelection(10);
    }
}