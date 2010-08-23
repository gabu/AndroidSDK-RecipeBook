package com.example.android.recipe064;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class OverlayView extends View {

    public OverlayView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        float endX = canvas.getWidth();
        float centerX = endX / 2.0f;
        float endY = canvas.getHeight();
        float centerY = endY / 2.0f;

        // 横線
        canvas.drawLine(0, centerY, endX, centerY, paint);
        // 縦線
        canvas.drawLine(centerX, 0, centerX, endY, paint);
    }
}
