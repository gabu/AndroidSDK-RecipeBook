package com.example.android.recipe060;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class TouchImageView extends ImageView {

    // 現在の状態のMatrix
    // これがタッチイベントのたびに変化する
    private Matrix mMatrix = new Matrix();

    // 前の状態を保持するためのMatrix
    // postTranslateが相対座標で扱われるため
    // 前の状態を保持しておいて、setでベースとしてセットする
    private Matrix mSavedMatrix = new Matrix();

    // 状態フラグ
    private static final int NONE = 0; // 何もしてない
    private static final int DRAG = 1; // ドラッグ中
    private static final int ZOOM = 2; // ズーム中

    // 現在の状態
    private int mMode = NONE;

    // ドラッグ開始座標を保持する
    private PointF mStartPoint = new PointF();

    // ズーム開始時の距離を保持する
    private float mOldDistance;

    // ズーム開始時の中点座標を保持する
    private PointF mMidPoint = new PointF();

    public TouchImageView(Context context) {
        super(context);
        init(context);
    }

    public TouchImageView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 初期化
    private void init(Context context) {
        // クリックできるように
        setClickable(true);

        // Matrixを使ってスケールする
        setScaleType(ScaleType.MATRIX);

        // OnTouchListenerをセット
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                // タッチ開始（ドラッグ開始）
                case MotionEvent.ACTION_DOWN:
                    mMode = DRAG;
                    mStartPoint.set(event.getX(), event.getY());
                    mSavedMatrix.set(mMatrix);
                    break;

                // タッチが移動している状態
                // ドラッグ中とズーム中で処理を切り替える
                case MotionEvent.ACTION_MOVE:
                    if(mMode == DRAG) {
                        mMatrix.set(mSavedMatrix);
                        float x = event.getX() - mStartPoint.x;
                        float y = event.getY() - mStartPoint.y;
                        mMatrix.postTranslate(x, y);
                    } else if (mMode == ZOOM) {
                        float newDist = culcDistance(event);
                        float scale = newDist / mOldDistance;
                        mMatrix.set(mSavedMatrix);
                        mMatrix.postScale(scale, scale,
                                          mMidPoint.x, mMidPoint.y);
                    }
                    break;

                // タッチ終了
                case MotionEvent.ACTION_UP:
                    mMode = NONE;
                    break;

                // マルチタッチ開始（ズーム開始）
                case MotionEvent.ACTION_POINTER_DOWN:
                    mMode = ZOOM;
                    mOldDistance = culcDistance(event);
                    culcMidPoint(mMidPoint, event);
                    break;

                // マルチタッチ終了（ズーム終了）
                case MotionEvent.ACTION_POINTER_UP:
                    mMode = NONE;
                    break;
                default:
                    break;
                }

                // postTranslateメソッドで座標
                // postScaleメソッドでスケール
                // が変化したMatrixをImageに反映させる。
                setImageMatrix(mMatrix);
                return true;
            }
        });
    }

    // マルチタッチの2点間の距離を計算して返します。
    private float culcDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    // マルチタッチの2点間の中点座標を計算してmidPointにセットします。
    private void culcMidPoint(PointF midPoint, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        midPoint.set(x / 2, y / 2);
    }
}
