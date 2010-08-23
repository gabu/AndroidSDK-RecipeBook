package com.example.android.recipe048;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Recipe048 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);

        // 元画像をリソースから読み込む
        Bitmap src = BitmapFactory.decodeResource(getResources(),
                                                  R.drawable.src);
        int srcWidth = src.getWidth();   // 元画像のwidth
        int srcHeight = src.getHeight(); // 元画像のheight

        // リサイズしたい新しい画像のサイズ
        Matrix matrix = new Matrix();

        float dstWidth = 50;  // リサイズ後のwidth
        float dstHeight = 50; // リサイズ後のheight

        // Scaleなので、新しいサイズ / 元のサイズ の値を設定する
        matrix.postScale(dstWidth / srcWidth, dstHeight / srcHeight);

        // 切り取り！
        Bitmap dst = Bitmap.createBitmap(src,  // 元画像
                                         20,   // 開始X座標
                                         20,   // 開始Y座標
                                         100,  // 切り取るwidth
                                         100); // 切り取るheight

        ImageView imageView01 = new ImageView(this);
        imageView01.setImageBitmap(src);

        ImageView imageView02 = new ImageView(this);
        imageView02.setImageBitmap(dst);

        linearLayout.addView(imageView01);
        linearLayout.addView(imageView02);
        setContentView(linearLayout);
    }
}