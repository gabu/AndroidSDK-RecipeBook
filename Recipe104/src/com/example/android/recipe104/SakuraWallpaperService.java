package com.example.android.recipe104;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class SakuraWallpaperService extends WallpaperService {

    private final Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // onCreateEngineでEngineを返します。
    @Override
    public Engine onCreateEngine() {
        return new SakuraEngine();
    }

    // 描画を担当するEngineです。
    public class SakuraEngine extends Engine {

        private Bitmap image;   // イメージ
        private int    x = 0;   // X座標
        private int    y = 0;   // Y座標
        private int    vx = 10; // X速度
        private int    vy = 10; // Y速度
        private int    width;
        private int    height;

        // 描画スレッド
        private final Runnable mDrawThread = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        // 表示状態フラグ
        private boolean mVisible;

        //コンストラクタ
        public SakuraEngine() {
            // リソースからイメージをロードしておきます。
            image = BitmapFactory.decodeResource(getResources(),
                                                 R.drawable.sakura);
        }

        // Engine生成時に呼び出される
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        // Engine破棄時に呼び出される
        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mDrawThread);
        }

        // 表示状態変更時に呼び出される
        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                drawFrame();
            } else {
                mHandler.removeCallbacks(mDrawThread);
            }
        }

        // サーフェイス変更時に呼び出される
        @Override
        public void onSurfaceChanged(SurfaceHolder holder,
            int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            this.width = width;
            this.height = height;
            drawFrame();
        }

        // サーフェイス生成時に呼び出される
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        // サーフェイス破棄時に呼び出される
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible = false;
            mHandler.removeCallbacks(mDrawThread);
        }

        // オフセット変更時に呼び出される
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
            float xStep, float yStep, int xPixels, int yPixels) {
            drawFrame();
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                vx =- vx;
                vy =- vy;
            }
        }

        // フレームの描画
        private void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                // Canvasをロック！
                c = holder.lockCanvas();
                if (c != null) {
                    // 描画
                    c.drawColor(Color.WHITE);
                    c.drawBitmap(image, x, y, null);
                }
            } finally {
                // Canvasをアンロック！
                if (c != null) holder.unlockCanvasAndPost(c);
            }

            // 跳ね返りの計算
            // 左端か右端に達したらx方向の移動を反転
            if (x < 0 || width - image.getWidth() < x) {
                vx=-vx;
            }
            // 上端か下端に達したらy方向の移動を反転
            if (y < 0 || height - image.getHeight() < y) {
                vy=-vy;
            }
            // 次の座標を計算
            x += vx;
            y += vy;

            // 次の描画をセット(以下は100ms)
            mHandler.removeCallbacks(mDrawThread);
            if (mVisible) mHandler.postDelayed(mDrawThread, 100);
        }
    }
}
