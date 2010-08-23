package com.example.android.recipe086;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Recipe086 extends Activity {

    private Uri mPictureUri;

    private static final int REQUEST_CODE_1 = 1;
    private static final int REQUEST_CODE_2 = 2;
    private static final int REQUEST_CODE_3 = 3;
    private static final int REQUEST_CODE_4 = 4;
    private static final int REQUEST_CODE_5 = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Recipe086
    public void onBrowserButton(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    // Recipe086
    public void onSearchButton(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        // 検索したい文字をセット
        intent.putExtra(SearchManager.QUERY, "マクドナルド");
        startActivity(intent);
    }

    // Recipe087
    public void onDialerButton(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }

    // Recipe087
    public void onCallButton(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
        // you need android.permission.CALL_PHONE
    }

    // Recipe088
    public void onSimpleCameraButton(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    // Recipe088
    public void onLargeCameraButton(View view) {
        String filename = "Recipe_" + System.currentTimeMillis() + ".jpg";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        mPictureUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent();
        // intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPictureUri);
        startActivityForResult(intent, REQUEST_CODE_2);
    }

    // Recipe089
    public void onVideoButton(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_3);
    }

    // Recipe090
    public void onRecorderButton(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, REQUEST_CODE_4);
    }

    // Recipe091
    public void onRecognizeButton(View view) {
        Intent intent = new Intent();
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.setAction(RecognizerIntent.ACTION_WEB_SEARCH);
        // 音声認識に使う言語モデルを指定
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        // プロンプトに表示する文字列を指定
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "好きな飲み物は？");
         startActivityForResult(intent, REQUEST_CODE_5);
    }

    public void onAppButton(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.example.android.recipe079",
                "com.example.android.recip079.Recipe079");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {

            // Activityが見つからなかった場合の対処例
            // 必要なアプリをインストールしてもらうなど
            Toast.makeText(getApplicationContext(),
                           "QRコードスキャナーをインストールしてください",
                           Toast.LENGTH_LONG).show();

            // マーケットを表示して、インストールを促す
            Uri uri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_VIEW);
            intent2.setData(uri);
            startActivity(intent2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            // 正しい結果が得られなかった場合の処理
            return;
        }

        if (requestCode == REQUEST_CODE_1) {
            // このbitmapが撮影した画像データです。
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);

        } else if (requestCode == REQUEST_CODE_2) {
            // dataがnullなので、以下のように取得しないで
            // Uri uri = data.getData();

            // インテントにセットしたUri mPictureUriを使う
            // エラーが発生していなければ、
            // このmPictureUriに撮影した写真データのUriが入っている。
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageURI(mPictureUri);

        } else if (requestCode == REQUEST_CODE_3) {
            // 撮影された動画のUriを取得
            Uri uri = data.getData();
            // VideoViewを取得
            VideoView v = (VideoView)findViewById(R.id.VideoView01);
            // VideoViewにはUriがセットできます。
            v.setVideoURI(uri);
            // 再生します。
            v.start();

        } else if (requestCode == REQUEST_CODE_4) {
            // 録音された音声のUriを取得
            Uri uri = data.getData();

            // メディアプレーヤーで再生する例
            MediaPlayer mp = new MediaPlayer();
            try {
                // Uriをセット
                mp.setDataSource(getApplicationContext(), uri);
                // 準備
                mp.prepare();
                // 再生！
                mp.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CODE_5) {
            String resultStr = "";
            // 認識結果のリストを取得
            // 似ている言葉など、複数の結果がある場合もある。
            List<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            for (String result : results) {
                resultStr += (result + "\n");
            }
            TextView textView = (TextView)findViewById(R.id.text);
            textView.setText(resultStr);
        }
    }
}
