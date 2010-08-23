package com.example.android.recipe037;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

public class Recipe037 extends Activity {

    private static final String TAG = "Recipe037";

    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mWebView = (WebView)findViewById(R.id.WebView01);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient()); // いらないっぽい
        mWebView.loadUrl("file:///android_asset/zflow.html");
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick");
        InputMethodManager imm;
        imm =
          (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        EditText editText = (EditText)findViewById(R.id.EditText01);
        String query = editText.getText().toString();
        mWebView.loadUrl("javascript:startLoading('"+query+"')");
    }
}
