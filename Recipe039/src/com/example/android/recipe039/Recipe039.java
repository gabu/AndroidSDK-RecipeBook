package com.example.android.recipe039;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Recipe039 extends Activity
    implements OnGesturePerformedListener {

    private GestureLibrary mLibrary;
    private GestureOverlayView mGesture;
    private WebView mWebView;

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
        mGesture = (GestureOverlayView)findViewById(R.id.gestures);
        mGesture.addOnGesturePerformedListener(this);
        // ジェスチャー受け付けを無効にしておく
        mGesture.setEnabled(false);

        mWebView = (WebView)findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.google.com/");
    }

    // ボタンが押されたら呼び出される
    public void onStartButton(View view) {
        // ジェスチャー受け付けを有効にする
        mGesture.setEnabled(true);
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
                if ("back".equals(prediction.name)) {
                    // 戻れるか判定して
                    if(mWebView.canGoBack()) {
                        // 戻る！
                        mWebView.goBack();
                    }
                }
            }
        }
        // ジェスチャー受け付けを無効にする
        overlay.setEnabled(false);
    }
}