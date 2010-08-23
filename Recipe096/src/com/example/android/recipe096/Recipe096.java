package com.example.android.recipe096;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

public class Recipe096 extends Activity {

    private EditText mEditText;
    private Uri mPictureUri;

    private static final int CODE_GALLERY  = 1;
    private static final int CODE_CAMERA   = 2;
    private static final int CODE_RECORDER = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mEditText = (EditText) findViewById(R.id.text);
    }

    // Memoボタンが押されたら呼び出されます。
    public void onMemoButtonClick(View view) {
        // 入力された文字列をセットして
        // Evernoteアプリを呼び出します。
        Intent intent = new Intent();
        intent.setClassName("com.evernote",
                        "com.evernote.ui.ShareWithEvernoteActivity");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                        mEditText.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Photoボタンが押されたら呼び出されます。
    public void onPhotoButtonClick(View view) {
        // ギャラリーを呼び出します。
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CODE_GALLERY);
    }

    // Cameraボタンが押されたら呼び出されます。
    public void onCameraButtonClick(View view) {
        // 標準のカメラアプリを呼び出します。
        String filename =
                "Recipe_" + System.currentTimeMillis() + ".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        mPictureUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPictureUri);
        startActivityForResult(intent, CODE_CAMERA);
    }

    // Audioボタンが押されたら呼び出されます。
    public void onAudioButtonClick(View view) {
        // ボイスレコーダーを呼び出します。
        Intent intent = new Intent();
        intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, CODE_RECORDER);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        // RESULT_OKじゃない場合は何もしない
        if (resultCode != RESULT_OK) return;
        // ギャラリー呼び出しの結果
        if (requestCode == CODE_GALLERY) {
            // 選択された写真のUriを取得
            Uri uri = data.getData();
            // Evernoteへ
            startEvernote("image/*", uri);
        // カメラの結果
        } else if (requestCode == CODE_CAMERA) {
            // 撮影した写真のUriをEvernoteへ
            startEvernote("image/jpeg", mPictureUri);
        // ボイスレコーダーの結果
        } else if (requestCode == CODE_RECORDER) {
            // 録音した音声ファイルのUriをEvernoteへ
            Uri uri = data.getData();
            startEvernote("audio/*", uri);
        }
    }

    // 指定されたMIMEタイプ、Uriを使ってEvernote公式アプリを呼び出します。
    private void startEvernote(String type, Uri uri) {
        Intent intent = new Intent();
        intent.setClassName("com.evernote",
                "com.evernote.ui.ShareWithEvernoteActivity");
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
