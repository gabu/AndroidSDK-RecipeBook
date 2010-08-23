package com.example.android.recipe072;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Recipe072 extends Activity
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
        // 明るさセンサーを取得
        List<Sensor> list =
            mSensorManager.getSensorList(Sensor.TYPE_LIGHT);

        // 取得できなければ何もしない
        if (list.size() < 1) return;

        // 明るさセンサーを取得
        Sensor sensor = list.get(0);

        // 明るさセンサーにリスナーを登録
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
        // 明るさセンサー以外は何もしない。
        if (event.sensor.getType() != Sensor.TYPE_LIGHT) {
            return;
        }
        // 値を取得
        float value = event.values[0];
        // TextViewに表示！
        mTextView.setText("ただいまの明るさは" + value + "lxです！");
    }
}