package com.example.android.recipe059;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Recipe059 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bitmapを作る
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);

        // Canvasを作る
        Canvas canvas;
        canvas = new Canvas(bitmap);
        // 背景を白で塗りつぶす
        canvas.drawColor(Color.WHITE);

        // Paintを作る
        Paint paint;
        paint = new Paint();
        // 色をマゼンダにして
        paint.setColor(Color.MAGENTA);
        // 線の太さを5にして
        paint.setStrokeWidth(5);
        // 描画スタイルを線のみ（塗りつぶさない）
        paint.setStyle(Paint.Style.STROKE);

        // 星を書きたいので5回ループ
        for (int i = 0; i < 5; i++) {
            // 星の一辺を描画
            canvas.drawLine(10, 70, 190, 70, paint);
            // キャンバスを(100, 100)を中心に回転する
            canvas.rotate(360 / 5, 100, 100);
        }

        // レイアウトを作る
        LinearLayout layout = new LinearLayout(this);
        // ImageViewを作って
        ImageView imageView = new ImageView(this);
        // Canvasによって描画されたBitmapをセット
        imageView.setImageBitmap(bitmap);
        layout.addView(imageView);
        setContentView(layout);
    }
}