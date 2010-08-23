package com.example.android.recipe084;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class Recipe084 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteOpenHelper helper =
            new MySQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        // dbを使った処理
        db.close();
    }
}