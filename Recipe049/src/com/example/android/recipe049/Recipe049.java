package com.example.android.recipe049;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Recipe049 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(new GIFView(this));
        setContentView(linearLayout);
    }

    private class GIFView extends View {

        private Movie movie;
        private long movieStart;

        public GIFView(Context context) {
            super(context);
            InputStream is;
            is = context.getResources().openRawResource(R.drawable.sample);
            movie = Movie.decodeStream(is);
            movieStart = 0;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            if (movie == null) {
                return;
            }
            long now = android.os.SystemClock.uptimeMillis();
            if (movieStart == 0) {
                movieStart = now;
            }
            int relTime = (int) ((now - movieStart) % movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas, 0, 0);
            this.invalidate();
        }
    }
}