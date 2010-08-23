package com.example.android.recipe046;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Recipe046 extends Activity {
    ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        // リストアイテムを追加
        for (int i = 0; i < 20; i++) {
            mAdapter.add("item_" + i);
        }

        // ListViewを取得
        ListView listView = (ListView) findViewById(R.id.ListView01);

        // ListViewにフッターを追加
        // 必ずsetAdapterの前に呼び出すこと
        listView.addFooterView(
                getLayoutInflater().inflate(R.layout.footer, null),
                null,
                true
        );

        // ListViewにAdapterを追加
        listView.setAdapter(mAdapter);

        // リスナーをセット
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                // クリックされたViewがフッターか判定
                if (view.getId() == R.id.Footer) {

                    // 表示する数字を計算
                    int count = mAdapter.getCount();
                    int max = count + 5;

                    for (;count < max; count++) {
                        // リストアイテムを追加
                        mAdapter.add("item_" + count);
                    }
                }
            }
        });
    }
}