package com.example.adnroid.recipe069;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adnroid.recipe069.ShakeListener.OnShakeListener;

public class Recipe069 extends Activity {

    private ShakeListener mShakeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // ShakeListenerのインスタンスを作って
        mShakeListener = new ShakeListener(this);
        // リスナーをセット
        mShakeListener.setOnShakeListener(new OnShakeListener() {
            // シェイクを検知すると
            // 以下のonShakeメソッドが呼び出されます。
            public void onShake() {
              // トーストを表示します。
              Toast.makeText(getApplicationContext(),
                             "onShake!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // アクティビティのonResumeメソッドで
        // ShakeListenerのonResumeメソッドを呼び出してください。
        // センサーの準備などをします。
        mShakeListener.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // アクティビティのonPauseメソッドで
        // ShakeListenerのonPauseメソッドを呼び出してください。
        // センサーのリスナーを解放します。
        mShakeListener.onPause();
    }
}