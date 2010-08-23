package com.example.android.recipe071;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximityManager implements SensorEventListener {

    // 近接センサーの変化を検知するリスナーです。
    public interface OnProximityListener {
        // センサーの値が変わったら呼び出されます。
        void onSensorChanged(SensorEvent event);
        // 遠ざかったら呼び出されます。
        void onFar(float value);
        // 近づいたら呼び出されます。
        void onNear(float value);
    }

    private SensorManager mSensorManager;
    private OnProximityListener mListener;

    private float mPreValue = -1;

    public ProximityManager(Context context) {
        mSensorManager = (SensorManager)context.getSystemService(
                                        Context.SENSOR_SERVICE);
    }

    public void setOnProximityListener(OnProximityListener listener) {
        mListener = listener;
    }

    public void onResume() {
        // 近接センサーを取得
        List<Sensor> list =
            mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY);

        // 取得できなければ何もしない
        if (list.size() < 1) return;

        // 近接センサーを取得
        Sensor sensor = list.get(0);

        // 近接センサーにリスナーを登録
        // 第3引数で感度を指定できます。
        mSensorManager.registerListener(this, sensor,
                                   SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        // リスナーを解除
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        // 近接センサー以外は何もしない。
        if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) {
            return;
        }
        // 初回は何もしない
        if (mPreValue == -1) {
            mPreValue = event.values[0];
            return;
        }
        float value = event.values[0];
        if (value < mPreValue) {
            // 前回より値が小さければ近づいたということなので
            // リスナーのonNearメソッドを呼び出す。
            if (mListener != null) mListener.onNear(value);
        } else if (value > mPreValue) {
            // 前回より値が大きければ遠ざかったということなので
            // リスナーのonFarメソッドを呼び出す。
            if (mListener != null) mListener.onFar(value);
        }
        // リスナーがセットされてれば
        // onSensorChangedメソッドを呼び出す
        if (mListener != null) mListener.onSensorChanged(event);
        // 今回の値を保存
        mPreValue = value;
    }
}
