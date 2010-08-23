package com.example.android.recipe045;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class Recipe045 extends Activity {

    private static final String TAG = "Recipe045";

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
        listView.setOnScrollListener(new OnScrollListener() {

            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
                // 実装が必須なので定義するが今回は使わない
            }

            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount) {
                Log.d(TAG, "++++++++++++++++");
                Log.d(TAG, "firstVisibleItem=" + firstVisibleItem);
                Log.d(TAG, "visibleItemCount=" + visibleItemCount);
                Log.d(TAG, "totalItemCount=" + totalItemCount);

                if ((totalItemCount - visibleItemCount) == firstVisibleItem) {
                    Toast.makeText(view.getContext(),
                                   "ここが最後です＞＜",
                                   Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}