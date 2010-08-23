package com.example.android.recipe066;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Recipe066 extends Activity {

    // プレビューのためのSurfaceView
    private SurfaceView mCameraView;

    // レコーダー
    private MediaRecorder mRecorder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // フルスクリーン表示にします
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // タイトルバーを非表示にします
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);

        // プレビューのためSurfaceHolderに
        // SURFACE_TYPE_PUSH_BUFFERSをセット
        mCameraView = (SurfaceView)findViewById(R.id.camera_view);
        SurfaceHolder holder = mCameraView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void onStartButton(View view) {
        // MediaRecorderを作って
        mRecorder = new MediaRecorder();
        // ビデオ入力にカメラをセット
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // オーディオ入力にマイクをセット
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 出力フォーマットに3gpをセット
        mRecorder.setOutputFormat(
                MediaRecorder.OutputFormat.THREE_GPP);

        // 出力ファイルパスを作って
        String path = createFilePath();
        // セット
        mRecorder.setOutputFile(path);

        // フレームレートをセット
        mRecorder.setVideoFrameRate(15);
        // 撮影サイズを指定
        // 端末のカメラに依存するので気をつけてください。
        mRecorder.setVideoSize(800, 480);
        // ビデオエンコーダーにMPEG_4_SPをセット
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        // オーディオエンコーダーにAMR_NBをセット
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        // プレビュー表示にSurfaceをセット
        mRecorder.setPreviewDisplay(
                mCameraView.getHolder().getSurface());
        try {
            // 準備して
            mRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 録画スタート！
        mRecorder.start();
    }

    public void onStopButton(View view) {
        // 録画を終了
        mRecorder.stop();
        // レコーダーを解放
        mRecorder.release();
    }

    private String createFilePath() {
        // SDカードのディレクトリ
        File dir = Environment.getExternalStorageDirectory();
        // アプリ名で
        File appDir = new File(dir, "Recipe066");
        // ディレクトリを作る
        if (!appDir.exists()) appDir.mkdir();
        // ファイル名
        String name = System.currentTimeMillis() + ".3gp";
        // 出力ファイルのパス
        return new File(appDir, name).getAbsolutePath();
    }
}