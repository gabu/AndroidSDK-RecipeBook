package com.example.android.recipe085;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "recipe.db";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DBが新規作成された時に呼び出される。
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL("CREATE TABLE IF NOT EXISTS people"
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  name TEXT,"
                + "  age INTEGER);");

        // 初期データを投入
        for (int i = 0; i < 20; i++) {
            ContentValues values = new ContentValues();
            values.put("name", "gabu_" + i);
            values.put("age", i);
            db.insert("people", null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
    }
}
