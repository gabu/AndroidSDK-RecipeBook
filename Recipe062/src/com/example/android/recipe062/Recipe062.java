package com.example.android.recipe062;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class Recipe062 extends Activity {

    private MediaRecorder mRecorder;
    private TextView mTextView;
    private boolean isRecording = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView)findViewById(R.id.TextView01);
    }

    // Startボタンが押されたら呼び出される
    public void onStartButton(View view) {
        if (isRecording) return;

        mTextView.setText("Recording...");

        // SDカードのディレクトリ
        File dir = Environment.getExternalStorageDirectory();
        // アプリ名で
        File appDir = new File(dir, "Recipe062");
        // ディレクトリを作る
        if (!appDir.exists()) appDir.mkdir();
        // ファイル名
        String name = System.currentTimeMillis() + ".3gp";
        // 出力ファイルのパス
        String path = new File(appDir, name).getAbsolutePath();

        mRecorder = new MediaRecorder();
        // 入力ソースにマイクを指定
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 出力フォーマットに3gpを指定
        mRecorder.setOutputFormat(
                MediaRecorder.OutputFormat.THREE_GPP);
        // 音声エンコーダにAMRを指定
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // 出力ファイルのパスを指定
        mRecorder.setOutputFile(path);
        try {
            // 準備して
            mRecorder.prepare();
            // 録音スタート！
            mRecorder.start();
            isRecording = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Stopボタンが押されたら呼び出される
    public void onStopButton(View view) {
        if (!isRecording) return;

        mTextView.setText("Recorded!");

        // 録音を停止して
        mRecorder.stop();
        // 解放
        mRecorder.release();
        isRecording = false;
    }
}