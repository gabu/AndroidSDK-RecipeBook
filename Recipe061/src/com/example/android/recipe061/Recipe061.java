package com.example.android.recipe061;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Recipe061 extends Activity {

    private MediaPlayer mMediaPlayer;

    private static final int MAX_SOUND_COUNT = 5;
    private SoundPool mSoundPool;
    private int[] mSounds = new int[MAX_SOUND_COUNT];

    // Prepared状態がMediaPlayerから取得できないので
    // 自前で管理するためのフラグ
    private boolean isPrepared = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onUntanButton(View v) {
        mSoundPool.play(mSounds[0], 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public void onUntanHighButton(View v) {
        mSoundPool.play(mSounds[0], 1.0F, 1.0F, 0, 0, 2.0F);
    }

    public void onUntanLowButton(View v) {
        mSoundPool.play(mSounds[0], 1.0F, 1.0F, 0, 0, 0.5F);
    }

    // ホームボタンで閉じて戻ってきた時に
    // MediaPlayerを正しい状態にしてあげるため
    // onCreateではなくonResumeで準備する
    @Override
    public void onResume() {
        super.onResume();

        mMediaPlayer = MediaPlayer.create(this, R.raw.sample);
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                // 準備ができて自動的に再生したいなら、ここでstartメソッドを呼ぶ
                // mp.start();
            }
        });

        mSoundPool = new SoundPool(MAX_SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        mSounds[0] = mSoundPool.load(this, R.raw.sample, 1);
    }


    // ホームボタンを押された時も解放するために
    // onDestroyではなくonPauseでrelease()する
    @Override
    public void onPause() {
        super.onPause();
        mMediaPlayer.release();
        mSoundPool.release();
    }

    public void onStartButton(View view) {
        if (isPrepared) {
            mMediaPlayer.start();
        } else {
            Toast.makeText(this, "音声ファイルをロード中でやんす",
                           Toast.LENGTH_LONG).show();
        }
    }

    public void onPauseButton(View view) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void onStopButton(View view) {
        if (!mMediaPlayer.isPlaying()) {
            return;
        }
        mMediaPlayer.stop();
        isPrepared = false;
        // もう一度再生されるために準備しておく
        try {
            // prepareAsync()ではなくてprepare()を呼ぶと
            // stopping状態にprepare()が要求されて
            // java.lang.IllegalStateExceptionが発生する
            mMediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
