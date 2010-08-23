package com.example.android.recipe063;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class LightView extends View {

    private AlphaAnimation mAnimation;
    private Random random = new Random();

    // 描画する色
    private int mColor = Color.BLACK;
    private int[] mColors = new int[] {
            Color.rgb(237,  26,  61), // 赤
            Color.rgb(255, 183,  76), // 橙
            Color.rgb(255, 212,   0), // 黄
            Color.rgb(  0, 128,   0), // 緑
            Color.rgb(  0, 154, 214), // 青
            Color.rgb( 35,  71, 148), // 藍
            Color.rgb(167,  87, 168)  // 紫
    };

    public LightView(Context context) {
        super(context);

        // だんだん透明になるAlphaAnimationを生成
        mAnimation = new AlphaAnimation(1, 0);
        // 3秒で動くように
        mAnimation.setDuration(3000);
        // 終了後、元に戻らないように
        mAnimation.setFillAfter(true);
    }

    public void randomDraw() {
        // アニメーション中に次のアニメーションが動いて
        // 欲しいのでキャンセルする
        clearAnimation();
        // 色をランダムに選んで
        mColor = mColors[random.nextInt(mColors.length - 1)];
        // アニメーション開始
        startAnimation(mAnimation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画面を指定された色で塗りつぶす
        canvas.drawColor(mColor);
    }
}
