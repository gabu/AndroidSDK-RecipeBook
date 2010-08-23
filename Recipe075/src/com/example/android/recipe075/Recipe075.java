package com.example.android.recipe075;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Recipe075 extends Activity
    implements SensorEventListener {

    private TextView mTextView;
    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView)findViewById(R.id.text);
        mSensorManager = (SensorManager)getSystemService(
                                                      SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 地磁気センサーを取得
        List<Sensor> list =
            mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);

        // 取得できなければ何もしない
        if (list.size() < 1) return;

        // 地磁気センサーを取得
        Sensor sensor = list.get(0);

        // 地磁気センサーにリスナーを登録
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

    // センサーの値が変化したら呼び出される
    public void onSensorChanged(SensorEvent event) {
        // 地磁気センサー以外は何もしない。
        if (event.sensor.getType() != Sensor.TYPE_MAGNETIC_FIELD) {
            return;
        }
        // 値を取得
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        // TextViewに表示！
        mTextView.setText("ただいまの地磁気\n"
                + "X方向に" + x + "uT\n"
                + "Y方向に" + y + "uT\n"
                + "Z方向に" + z + "uT\n"
                + "です！");
    }
}