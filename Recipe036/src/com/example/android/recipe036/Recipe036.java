package com.example.android.recipe036;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Recipe036 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebView webView = (WebView)findViewById(R.id.WebView01);
        webView.loadUrl("file:///android_asset/help.html");
//        String html = "<!DOCTYPE html>";
//        html += "<html lang=\"ja\">";
//        html += "<head>";
//        html += "<meta charset=\"utf-8\">";
//        html += "</head>";
//        html += "<body>";
//        html += "<h1>ヘルプ</h1><h2>大分省略してます。</h2>";
//        html += "</body></html>";
//        webView.loadData(html, "text/html", "utf-8");
    }
}