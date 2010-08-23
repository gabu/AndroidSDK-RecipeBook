package com.example.recipe052;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    public static final int DRAW_POINT  = 1;
    public static final int DRAW_LINE   = 2;
    public static final int DRAW_RECT   = 3;
    public static final int DRAW_CIRCLE = 4;
    public static final int DRAW_OVAL   = 5;
    public static final int DRAW_ARC    = 6;
    public static final int DRAW_PATH   = 7;

    private int mDrawMode = DRAW_POINT;

    public void setDrawMode(int drawMode) {
        mDrawMode = drawMode;
    }

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mDrawMode) {
        case DRAW_POINT:
            drawPoint(canvas);
            break;
        case DRAW_LINE:
            drawLine(canvas);
            break;
        case DRAW_RECT:
            drawRect(canvas);
            break;
        case DRAW_CIRCLE:
            drawCircle(canvas);
            break;
        case DRAW_OVAL:
            drawOval(canvas);
            break;
        case DRAW_ARC:
            drawArc(canvas);
            break;
        case DRAW_PATH:
            drawPath(canvas);
            break;
        }
    }

    private void drawPath(Canvas canvas) {

        Path path = new Path();

        // 弧を追加
        RectF oval = new RectF(10, 10, 60, 60);
        path.addArc(oval, 180, 90);

        // 円を追加
        path.addCircle(95, 35, 25, Path.Direction.CW);

        // 楕円を追加
        RectF oval2 = new RectF(130, 10, 230, 60);
        path.addOval(oval2, Path.Direction.CW);

        // 四角形を追加
        RectF rect = new RectF(10, 70, 60, 120);
        path.addRect(rect, Path.Direction.CW);

        // 角丸四角形を追加
        RectF rect2 = new RectF(70, 70, 120, 120);
        path.addRoundRect(rect2, 5, 5, Path.Direction.CW);

        // 現在座標を移動
        path.moveTo(130, 70);

        // 弧を追加
        RectF oval3 = new RectF(130, 70, 180, 120);
        // trueを付けると始点にmoveToする
        // 付けないと現在座標から始点まで線が引かれてしまう
        path.arcTo(oval3, 180, 90, true);

        // 現在座標を移動
        path.moveTo(10, 130);

        // 3次ベジェ曲線を追加
        path.rCubicTo(100, 25, 25, 100, 100, 100);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawPath(path, paint);
    }

    private void drawArc(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        // 1/4円
        RectF rect1 = new RectF(30, 30, 90, 90);
        canvas.drawArc(rect1, 0, 90, true, paint);

        // 半円
        RectF rect2 = new RectF(120, 30, 180, 90);
        canvas.drawArc(rect2, 180, 180, true, paint);

        // 4番目と比べる用
        RectF rect3 = new RectF(30, 120, 90, 180);
        canvas.drawArc(rect3, 270, 120, true, paint);

        // useCenterをfalse
        RectF rect4 = new RectF(120, 120, 180, 180);
        canvas.drawArc(rect4, 270, 120, false, paint);
    }

    private void drawOval(Canvas canvas) {
       Paint paint = new Paint();
       paint.setColor(Color.WHITE);
       paint.setAntiAlias(true);

       RectF rect = new RectF(30, 30, 90, 60);
       canvas.drawOval(rect, paint);

       RectF rect2 = new RectF(120, 30, 150, 90);
       canvas.drawOval(rect2, paint);
    }

    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        canvas.drawCircle(100, 100, 50, paint);

        paint.setAntiAlias(true);
        canvas.drawCircle(200, 100, 50, paint);
    }

    private void drawRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);

        // RectFを指定
        RectF rect = new RectF(30, 30, 60, 60);
        canvas.drawRect(rect, paint);

        // default
        // Paint.Style.FILL
        // 塗りつぶすだけ（線を引かない）
        canvas.drawRect(90, 30, 120, 60, paint);

        // 線を引くだけ（塗りつぶさない）
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(30, 90, 60, 120, paint);

        // 線を引いて、かつ塗りつぶす
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(90, 90, 120, 120, paint);
    }

    private void drawLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);

        // default
        canvas.drawLine(30, 30, 100, 30, paint);

        // 直線の始端と終端で何もしません。
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(30, 50, 100, 50, paint);

        // 直線の始端と終端を丸くします。
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(30, 70, 100, 70, paint);

        // 直線の始端と終端を四角形にします。
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(30, 90, 100, 90, paint);

        // 座標の配列を指定
        // [x0 y0 x1 y1 x2 y2 ...]
        // 座標4つで直線1本
        float[] pts = { 150, 30, 220, 30, 150, 50, 220, 50 };
        canvas.drawLines(pts, paint);
    }

    private void drawPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
//        paint.setAntiAlias(true);
        canvas.drawPoint(50, 100, paint);
        canvas.drawPoint(60, 110, paint);

        float[] pts = { 10, 10, 20, 20, 30, 30, 40, 40 };
        canvas.drawPoints(pts, paint);

        float[] pts2 = { 100, 10, 100, 20, 100, 30, 100, 40 };
        canvas.drawPoints(pts2, 2, 4, paint);
    }
}
