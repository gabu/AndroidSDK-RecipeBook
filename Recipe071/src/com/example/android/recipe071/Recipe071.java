package com.example.android.recipe071;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.recipe071.ProximityManager.OnProximityListener;

public class Recipe071 extends Activity {

    private ProximityManager mManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mManager = new ProximityManager(this);
        mManager.setOnProximityListener(new OnProximityListener() {

            // 近接センサーの値が変わる度に呼び出されます。
            public void onSensorChanged(SensorEvent event) {
            }

            // 近接センサーに近づいたら呼び出されます。
            public void onNear(float value) {
                Toast.makeText(getApplicationContext(),
                               "onNear!", Toast.LENGTH_SHORT).show();
            }

            // 近接センサーから遠ざかったら呼び出されます。
            public void onFar(float value) {
                Toast.makeText(getApplicationContext(),
                           "onFar!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // アクティビティのonResumeメソッドで
        // ProximityManagerのonResumeメソッドを呼び出してください。
        // センサーの準備などをします。
        mManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // アクティビティのonPauseメソッドで
        // ProximityManagerのonPauseメソッドを呼び出してください。
        // センサーのリスナーを解放します。
        mManager.onPause();
    }
}