package com.example.android.recipe051;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Recipe051 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bitmap srcBitmap;
        srcBitmap = BitmapFactory.decodeResource(getResources(),
                                                 R.drawable.sample);

        Bitmap reflectionBitmap;
        reflectionBitmap = createReflectionBitmap(srcBitmap);

        LinearLayout layout = new LinearLayout(this);

        ImageView imageView01 = new ImageView(this);
        imageView01.setImageBitmap(srcBitmap);
        layout.addView(imageView01);

        ImageView imageView02 = new ImageView(this);
        imageView02.setImageBitmap(reflectionBitmap);
        layout.addView(imageView02);

        setContentView(layout);
    }

    public static Bitmap createReflectionBitmap(Bitmap src) {
        // 元画像と反射画像のマージン
        int margin = 4;
        int width = src.getWidth();
        int height = src.getHeight();

        // 上下反転させるMatrix
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // 元画像の下半分を反転したBitmapを生成
        Bitmap reflection;
        reflection = Bitmap.createBitmap(src,
                                         0,
                                         height / 2,
                                         width,
                                         height / 2,
                                         matrix,
                                         true);
        // 元画像と反射画像が合体した結果画像のBitmapを生成
        // これはまだ空っぽ
        Bitmap result;
        result = Bitmap.createBitmap(width,
                                     height + height / 2,
                                     Config.ARGB_8888);
        // キャンバスを使って
        Canvas canvas;
        canvas = new Canvas(result);
        // 結果画像に元画像を書き込む
        canvas.drawBitmap(src, 0, 0, null);
        // 結果画像に反射画像を書き込む
        canvas.drawBitmap(reflection, 0, height + margin, null);

        // ここまでで反転画像と合体した状態
        // ここからぼかし
        Paint paint = new Paint();
        // LinearGradientという直線方向のグラデーションを使います。
        LinearGradient shader;
        // だんだん透明になるグラデーションを作る
        // 0x90ffffff(やや透明) -> 0x00FFFFFF(完全透明)
        shader = new LinearGradient(0,
                                    height,
                                    0,
                                    result.getHeight() + margin,
                                    0x90ffffff,
                                    0x00ffffff,
                                    TileMode.CLAMP);
        // Shaderクラスを継承しているのでsetShaderでPaintにセットします。
        paint.setShader(shader);
        // 色の変換モードを指定
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // グラデーションを描画
        canvas.drawRect(0, height, width, result.getHeight(), paint);

        return result;
    }
}