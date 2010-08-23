package com.example.android.recip079;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Recipe079 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onMailButton(View view) {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_SENDTO);
      intent.setData(Uri.parse("mailto:hoge@example.com"));
      intent.putExtra(Intent.EXTRA_SUBJECT, "件名です。");
      intent.putExtra(Intent.EXTRA_TEXT, "本文です。\n本文です。");
      startActivity(intent);

        /* 添付ファイルを付ける場合
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String[] to = {"hoge@example.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "件名です。");
        intent.putExtra(Intent.EXTRA_TEXT, "本文です。\n本文です。");
        // 添付ファイル
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "tmp.jpg");
        intent.putExtra(Intent.EXTRA_STREAM,
                        Uri.parse("file://"+ file.getAbsolutePath()));
        intent.setType("image/jpeg");
        startActivity(Intent.createChooser(intent,
                                           "メールアプリを選択してください"));
        */
    }
}