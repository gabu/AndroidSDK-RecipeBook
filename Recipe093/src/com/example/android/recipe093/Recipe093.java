package com.example.android.recipe093;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Recipe093 extends Activity {
    private static final String TAG = "Recipe093";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 外部ストレージにある音楽データのUri
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 取得するカラム（今回は無指定=すべて）
        String[] projection = null;
        // 検索条件
        String selection = null;
        // 検索条件の値
        String[] selectionArgs = null;
        // ソート条件
        String sortOrder = null;
        // 検索結果をCursorで取得しつつ、
        // カーソルの管理をアクティビティに任せる
        Cursor cursor = managedQuery(uri, projection, selection,
                                     selectionArgs, sortOrder);

        for (String column : cursor.getColumnNames()) {
            Log.d(TAG, column);
        }

        // ListViewに表示するカラム名の配列
        String[] from = {
                // タイトル
                MediaStore.Audio.AudioColumns.TITLE,
                // アーティスト名
                MediaStore.Audio.AudioColumns.ARTIST,
                // 曲の長さ
                MediaStore.Audio.AudioColumns.DURATION };

        // fromに対応するビューのリソースID
        int[] to = {
                R.id.title,
                R.id.artist,
                R.id.duration};

        // SimpleCursorAdapterを生成
        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(getApplicationContext(),
                                          R.layout.row,
                                          cursor,
                                          from,
                                          to);

        // Viewにセットする値を加工するためViewBinderをセット
        adapter.setViewBinder(new AudioListViewBinder());

        // ListViewにAdapterをセット
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    // Viewにセットする値を加工するクラス
    // SimpleCursorAdapter.ViewBinderインタフェースを実装します。
    private class AudioListViewBinder
        implements SimpleCursorAdapter.ViewBinder {

        public boolean setViewValue(View view,
                                    Cursor cursor,
                                    int columnIndex) {
            // 各カラムのインデックスを取得
            int titleIndex = cursor.getColumnIndex(
                    // タイトル
                    MediaStore.Audio.AudioColumns.TITLE);
            int artistIndex = cursor.getColumnIndex(
                    // アーティスト名
                    MediaStore.Audio.AudioColumns.ARTIST);
            int durationIndex = cursor.getColumnIndex(
                    // 再生時間
                    MediaStore.Audio.AudioColumns.DURATION);

            // 引数のcolumnIndexを比較する
            if (columnIndex == titleIndex
                       || columnIndex == artistIndex) {
              // タイトルとアーティスト名はそのままセットする
              ((TextView)view).setText(cursor.getString(columnIndex));
            } else if (columnIndex == durationIndex) {
                // 再生時間はミリ秒で取得できるので
                // mm:ssに加工
                Time time = new Time();
                time.set(cursor.getLong(columnIndex));
                ((TextView)view).setText(time.format("%M:%S"));
            }
            return true;
        }
    }
}