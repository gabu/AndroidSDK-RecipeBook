package com.example.android.recipe038;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.widget.Toast;

public class Recipe038 extends Activity
    implements OnGesturePerformedListener {

    private GestureLibrary mLibrary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Gestures Builderで作ったジェスチャー情報をロード
        mLibrary = GestureLibraries.fromRawResource(this,
                                                    R.raw.gestures);
        if (!mLibrary.load()) {
            finish();
        }

        // GestureOverlayViewにリスナーをセット
        GestureOverlayView gestures;
        gestures = (GestureOverlayView)findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
    }

    public void onGesturePerformed(GestureOverlayView overlay,
                                   Gesture gesture) {
        // 解析します
        ArrayList<Prediction> predictions;
        predictions = mLibrary.recognize(gesture);

        // 結果が1つ以上あったら
        if (predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            // スコアが1.0以上
            // （これはアプリの特性に応じて調整してください）
            if (prediction.score > 1.0) {
                // ジェスチャーの名前をトーストでチン
                Toast.makeText(this, prediction.name,
                               Toast.LENGTH_SHORT).show();
            }
        }
    }
}