package com.example.android.recipe063;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Recipe063 extends Activity {

    private SoundSwitch mSoundSwitch;
    private LightView mLightView;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLightView = new LightView(this);
        setContentView(mLightView);

        // 暗くならないようにする
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSoundSwitch = new SoundSwitch();
        // リスナーを登録
        mSoundSwitch.setOnVolumeReachedListener(
            new SoundSwitch.OnReachedVolumeListener() {
                // 音を感知したら呼び出される
                public void onReachedVolume(short volume) {
                    // 別スレッドからUIスレッドに要求するので
                    // Handler.postを使う
                    mHandler.post(new Runnable() {
                        public void run() {
                            mLightView.randomDraw();
                        }
                    });
                }
        });
        // 別スレッドとしてSoundSwitchを開始（録音を開始）
        new Thread(mSoundSwitch).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 録音を停止
        mSoundSwitch.stop();
    }
}