package com.example.android.recipe041;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Recipe041 extends Activity {

    private static final String TAG = "Recipe041";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // アイコン画像を生成
        Bitmap defaultImage;
        defaultImage = BitmapFactory.decodeResource(
                           getResources(), R.drawable.default_image);

        // テストデータを作成
        List<ListItem> list = new ArrayList<ListItem>();
        ListItem item1 = new ListItem();
        item1.image = defaultImage;
        item1.name = "gabu";
        item1.comment = "検索なら http://google.com/ がオススメ。";
        list.add(item1);
        ListItem item2 = new ListItem();
        item2.image = defaultImage;
        item2.name = "gabu";
        item2.comment = "連絡先は tsukada.shouya@gmail.com です！";
        list.add(item2);
        ListItem item3 = new ListItem();
        item3.image = defaultImage;
        item3.name = "gabu";
        item3.comment = "電話 090-9999-9999";
        list.add(item3);
        ListItem item4 = new ListItem();
        item4.image = defaultImage;
        item4.name = "gabu";
        item4.comment = "Address: 620 Eighth Avenue New York, NY 10018";
        list.add(item4);
        ListItem item5 = new ListItem();
        item5.image = defaultImage;
        item5.name = "gabu";
        item5.comment = "日本表記だと？住所: 〒460-0031 愛知県名古屋市中区本丸１−１";
        list.add(item5);

        // ListItemAdapterを生成
        ListItemAdapter adapter;
        adapter = new ListItemAdapter(this, 0, list);

        // ListViewにListItemAdapterをセット
        ListView listView = (ListView) findViewById(R.id.ListView01);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
//                ListItem item = (ListItem)listView.getSelectedItem(); // itemがnull
                ListItem item = (ListItem)listView.getItemAtPosition(position);
                Log.d(TAG, "選択されたアイテムのcomment=" + item.comment);
                TextView textView = (TextView)view.findViewById(R.id.name);
                Log.d(TAG, "選択されたViewのTextView(name)のtext=" + textView.getText());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemLongClick position=" + position);
                // ちなみに、falseを返すとイベントが継続するのでonItemClickも呼び出されます。
                return true;
            }
        });
    }
}