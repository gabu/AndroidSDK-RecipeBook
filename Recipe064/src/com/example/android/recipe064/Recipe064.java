package com.example.android.recipe064;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class Recipe064 extends Activity {

    // ビューを重ねて表示するためのレイアウト
    private FrameLayout mFrameLayout;
    // カメラプレビューのためのSurfaceView
    private SurfaceView mCameraView;
    // 重ねるビュー
    private OverlayView mOverlayView;

    // カメラ
    private Camera mCamera;

    private SurfaceHolder.Callback mSurfaceHolderCallback =
                                        new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // カメラを起動（オープン）します
            mCamera = Camera.open();
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder,
                int format, int width, int height) {
            // カメラのプレビューを開始します
            mCamera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // カメラのプレビューを停止します
            mCamera.stopPreview();
            // カメラを停止（解放）します
            mCamera.release();
            mCamera = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // フルスクリーン表示にします
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // タイトルバーを非表示にします
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // レイアウトとビューを生成して
        mFrameLayout = new FrameLayout(this);
        mCameraView = new SurfaceView(this);
        mOverlayView = new OverlayView(this);

        // SurfaceHolderにコールバックとタイプを指定
        SurfaceHolder holder = mCameraView.getHolder();
        holder.addCallback(mSurfaceHolderCallback);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // 重ねます
        mFrameLayout.addView(mCameraView);
        // 後にaddViewしたビューが手前に表示されます。
        mFrameLayout.addView(mOverlayView);
        setContentView(mFrameLayout);
    }
}