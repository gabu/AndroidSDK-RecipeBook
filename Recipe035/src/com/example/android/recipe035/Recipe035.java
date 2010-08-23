package com.example.android.recipe035;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Recipe035 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.google.com/");

        new AlertDialog.Builder(this)
        .setPositiveButton("OK", null)
        // WebViewをセットする
        .setView(webView)
        .create()
        .show();
    }
}