package com.example.android.recipe015;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Recipe015 extends Activity {

    private static final String TAG = "Recipe015";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onPutButton(View view) {
        // プリファレンスを取得
        SharedPreferences sp;
        sp = getPreferences(MODE_PRIVATE);

        // 編集するためのEditorを取得
        Editor editor = sp.edit();

        // 型に合わせたputメソッドでキーと値をセット
        editor.putBoolean("ex_boolean", true);
        editor.putFloat("ex_float", 0.1F);
        editor.putInt("ex_int", 123);
        editor.putLong("ex_long", 9999L);
        editor.putString("ex_string", "gabu");

        // 保存！
        editor.commit();
    }

    public void onGetButton(View view) {
        // プリファレンスを取得
        SharedPreferences sp;
        sp = getPreferences(MODE_PRIVATE);

        // 型に合わせたgetメソッドで値を取得
        boolean ex_boolean = sp.getBoolean("ex_boolean", false);
        Log.d(TAG, "ex_boolean=" + ex_boolean);

        float ex_float = sp.getFloat("ex_float", 0);
        Log.d(TAG, "ex_float=" + ex_float);

        int ex_int = sp.getInt("ex_int", 0);
        Log.d(TAG, "ex_int=" + ex_int);

        long ex_long = sp.getLong("ex_long", 0);
        Log.d(TAG, "ex_long=" + ex_long);

        String ex_string = sp.getString("ex_string", "");
        Log.d(TAG, "ex_string=" + ex_string);
    }
}