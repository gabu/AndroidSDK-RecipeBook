package com.example.android.recipe103;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class MyActivity extends Activity {
    static Map<Integer, CharSequence> map = new HashMap<Integer, CharSequence>();

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity);

        // EditText
        EditText editText = (EditText)findViewById(R.id.EditText01);

        // Widget の id を受け取る
        final Uri uri = getIntent().getData();
        // 過去のデータを表示
        if (uri != null) {
            int id = (int )ContentUris.parseId(uri);
            editText.setText(loadText(id));
        }

        // Button
        Button button = (Button)findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Context context = MyActivity.this;
                    AppWidgetManager manager =
                        AppWidgetManager.getInstance(context);
                    RemoteViews remoteViews = new RemoteViews(
                        context.getPackageName(), R.layout.widget);

                    EditText editText =
                        (EditText)findViewById(R.id.EditText01);
                    CharSequence text = editText.getText();
                    if (uri != null) {
                        int id = (int)ContentUris.parseId(uri);
                        Log.d("MyActivity", "id:" + id);
                        // 入力された値を保存
                        saveText(id, text);
                        // Widget 表示を更新
                        remoteViews.setTextViewText(R.id.TextView01,
                                                    text);
                        manager.updateAppWidget(id, remoteViews);
                    }

                    finish();
                }
            });
    }

    // 各AppWidgetの値をid 毎に覚えておく
    static private void saveText(int id, CharSequence text) {
        map.put(id, text);
    }

    static private CharSequence loadText(int id) {
        if (! map.containsKey(id)) {
            saveText(id, "");
        }
        return (CharSequence)map.get(new Integer(id));
    }
}
