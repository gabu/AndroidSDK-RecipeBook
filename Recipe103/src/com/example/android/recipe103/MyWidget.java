package com.example.android.recipe103;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {

    public static final Uri CONTENT_URI =
        Uri.parse("content://com.example.android.recipe103.mywidget");

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            Log.d("MyWidget", "onUpdate:" + id);

            // 起動するActivity
            Intent intent = new Intent(context, MyActivity.class);
            // Activity に渡すために AppWidget IDを設定する
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
            intent.setData(uri);

            // PendingIntentを取得
            PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.widget);

            // イベント対象の登録
            remoteViews.setOnClickPendingIntent(R.id.TextView01,
                                                pendingIntent);
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }
}
