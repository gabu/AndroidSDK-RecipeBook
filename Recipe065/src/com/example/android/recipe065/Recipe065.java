package com.example.android.recipe065;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Recipe065 extends Activity {

    private static final int REQUEST_CODE = 1;
    private Bitmap mBitmap = null;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImageView = (ImageView)findViewById(R.id.choice_image);
    }

    // ボタンがクリックされると呼び出されます。
    public void onChoiceButton(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // recycleメソッドでBitmapを解放する。
            // この1行をコメントアウトすると
            // 2枚目の写真を選んだ時点で
            // java.lang.OutOfMemoryErrorが発生します。
            if (mBitmap != null) mBitmap.recycle();
            InputStream in = null;
            try {
                Uri uri = data.getData();
                in = getContentResolver().openInputStream(uri);
                mBitmap = BitmapFactory.decodeStream(in);
                mImageView.setImageBitmap(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}