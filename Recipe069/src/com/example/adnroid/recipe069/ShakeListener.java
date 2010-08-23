package com.example.adnroid.recipe069;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener
    implements SensorEventListener {

    private SensorManager mSensorManager;
    private OnShakeListener mListener;
    private long mPreTime;
    private float mLastX;
    private float mLastY;
    private float mLastZ;
    private int mShakeCount;

    // シェイクを感知した時にonShakeメソッドを呼び出します。
    // setOnShakeListenerメソッドでセットしてください。
    public interface OnShakeListener {
        void onShake();
    }

    // OnShakeListenerをセット
    public void setOnShakeListener(OnShakeListener listener) {
        mListener = listener;
    }

    public ShakeListener(Context context) {
        // SensorManagerのインスタンスを取得
        mSensorManager = (SensorManager)context.getSystemService(
                                              Context.SENSOR_SERVICE);
    }

    public void onResume() {
        // 加速度センサーを取得
        List<Sensor> list =
            mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // 取得できなければ何もしない
        if (list.size() < 1) return;

        // 加速度センサーにリスナーを登録
        // 第3引数で感度を指定できます。
        // 今回はUIに使う想定でSENSOR_DELAY_UIにしました。
        mSensorManager.registerListener(this,
                                       list.get(0),
                                       SensorManager.SENSOR_DELAY_UI);
    }

    public void onPause() {
        // リスナーを解除
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // センサーの値が変わったら呼び出される
    public void onSensorChanged(SensorEvent event) {
        // センサーのタイプが加速度センサーじゃなかったら何もしない
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        long curTime = System.currentTimeMillis();
        long diffTime = curTime - mPreTime;
        // 物凄い頻度でイベントが発生するので
        // 100msに1回計算するように間引く
        if (diffTime > 100) {
            // 現在の値をとって
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            // 前回の値との差からスピードを計算
            float speed = Math.abs(x+y+z - mLastX-mLastY-mLastZ)
                          / diffTime * 10000;
            // スピードが300以上なら（お好みで変えてください）
            if (speed > 300) {
                // シェイクカウントをインクリメント
                mShakeCount++;
                // 4回連続スピードが300以上なら
                // シェイクと認定（お好みで変えてください）
                if (mShakeCount > 3) {
                    mShakeCount = 0;
                    // リスナーがセットされていれば
                    if (mListener != null) {
                        // onShakeメソッドを呼び出す
                        mListener.onShake();
                    }
                }
            } else {
                // 300以下ならリセット
                mShakeCount = 0;
            }
            // 前回値として保存
            mPreTime = curTime;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
        }
    }
}
