package com.example.android.recipe025;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

public class Recipe025 extends Activity {

    private static final String TAG = "Recipe025";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PackageManager pm = getPackageManager();
        // 起動用アクティビティの定義を準備
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // アプリ一覧を取得！
        final List<ResolveInfo> apps;
        apps = pm.queryIntentActivities(mainIntent, 0);
        // 必須ではないがアプリ名でソートする
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(pm));

        // アプリ一覧をループ
        for (ResolveInfo resolveInfo : apps) {
            // アプリ名を取得
            String AppName = resolveInfo.loadLabel(pm).toString();
            Log.d(TAG, AppName);
            // アクティビティ名を取得
            String fullClassName = resolveInfo.activityInfo.name;
            Log.d(TAG, fullClassName);
        }
    }
}