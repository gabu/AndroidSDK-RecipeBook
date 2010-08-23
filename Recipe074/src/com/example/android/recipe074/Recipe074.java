package com.example.android.recipe074;

import java.util.List;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

public class Recipe074 extends Activity
    implements SensorEventListener {

    // センサーマネージャー
    private SensorManager mSensorManager;
    // 方位磁針の代わりの画像
    private ImageView mImageView;
    // ディスプレイの中心座標
    private float mCenterX;
    private float mCenterY;
    // ImageViewを回転させるためのMatrix
    private Matrix mMatrix;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSensorManager = (SensorManager)getSystemService(
                                                      SENSOR_SERVICE);
        mImageView = (ImageView)findViewById(R.id.image);
        mMatrix = new Matrix();

        // android:scaleType="matrix"にすると
        // 自力で画面中央に表示しなければいけないので
        // 頑張って計算
        // もっと簡単な方法があれば是非教えてください。

        // ディスプレイのサイズを取得
        Display display = getWindowManager().getDefaultDisplay();
        int dispWidth = display.getWidth();
        int dispHeight = display.getHeight();
        // 画像のサイズを取得
        Drawable d = getResources().getDrawable(R.drawable.src);
        int imgWidth = d.getIntrinsicWidth();
        int imgHeight = d.getIntrinsicHeight();
        // これは中央に移動するために
        // 中心座標から画像のサイズの半分だけ
        // 左上にずらした座標
        float x = (dispWidth - imgWidth) / 2;
        float y = (dispHeight - imgHeight) / 2;
        // 中央に移動！
        mMatrix.postTranslate(x, y);
        mImageView.setImageMatrix(mMatrix);
        // 中心座標を回転時に使うので保持
        mCenterX = dispWidth / 2f;
        mCenterY = dispHeight / 2f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 傾きセンサーを取得
        List<Sensor> list =
            mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

        // 取得できなければ何もしない
        if (list.size() < 1) return;

        // 傾きセンサーを取得
        Sensor sensor = list.get(0);

        // 傾きセンサーにリスナーを登録
        // 第3引数で感度を指定できます。
        mSensorManager.registerListener(this, sensor,
                                   SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        // リスナーを解除
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private float mPreValue = 0;

    // センサーの値が変化したら呼び出される
    public void onSensorChanged(SensorEvent event) {
        // 傾きセンサー以外は何もしない。
        if (event.sensor.getType() != Sensor.TYPE_ORIENTATION) {
            return;
        }
        // 値を取得
        float value = event.values[0];
        // Matrixに回転する角度と中心座標をセット
        mMatrix.postRotate(mPreValue - value, mCenterX, mCenterY);
        // 回転！
        mImageView.setImageMatrix(mMatrix);
        // 今回の値を保持
        mPreValue = value;
    }
}