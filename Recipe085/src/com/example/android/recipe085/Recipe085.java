package com.example.android.recipe085;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Recipe085 extends Activity {

    SQLiteDatabase mDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onResume() {
        super.onResume();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        mDB = helper.getReadableDatabase();

        String where = "age >= ?";
        String[] args = {"5"};

        Cursor cursor;
        cursor = mDB.query("people", null, where, args, null, null, null);

        // カーソルのライフサイクル管理を
        // アクティビティに任せます。
        startManagingCursor(cursor);

        // from: カラム名の配列
        String[] from = {"name", "age"};

        // to: fromに対応するビューのIDの配列
        int[] to = { R.id.name_in_list, R.id.age_in_list };

        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(getApplicationContext(),
                                          R.layout.row,
                                          cursor,
                                          from,
                                          to);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public void onPause() {
        super.onPause();
        if (mDB != null) mDB.close();
    }
}