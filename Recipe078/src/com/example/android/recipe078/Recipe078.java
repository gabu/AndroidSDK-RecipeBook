package com.example.android.recipe078;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;

public class Recipe078 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 名古屋の明日の天気情報を取得できるURLです。
        String uri = "http://weather.livedoor.com/forecast/" +
                     "webservice/rest/v1?city=38&day=tomorrow";

        // HTTPクライアントを作って
        HttpClient client = new DefaultHttpClient();
        // GETオブジェクトを作って
        HttpGet get = new HttpGet();
        try {
            // URIをセット
            get.setURI(new URI(uri));
            // GETリクエストを実行してレスポンスを取得
            HttpResponse res = client.execute(get);
            // レスポンスからInputStreamを取得
            InputStream in = res.getEntity().getContent();
            // XMLプルパーサを生成して
            XmlPullParser parser = Xml.newPullParser();
            // InputStreamをセット
            parser.setInput(in, "UTF-8");

            int eventType = parser.getEventType();
            // イベントタイプがEND_DOCUMENTになるまでループ
            // けど、以下の必要な値が全て取得できたらbreakする。
            String title = "";
            String telop = "";
            String description = "";
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    if ("title".equals(tag)) {
                        // タイトルを取得
                        title = parser.nextText();
                    } else if ("telop".equals(tag)) {
                        // 天気を取得
                        telop = parser.nextText();
                    } else if ("description".equals(tag)) {
                        // 天気概況文を取得
                        description = parser.nextText();
                    }
                    break;
                }
                if ( ! ("".equals(title)
                        || "".equals(telop)
                        || "".equals(description))) {
                    // 必要な値が取得できたらループを抜ける
                    break;
                }
                // パーサを次のイベントまで進める
                eventType = parser.next();
            }
            // TextViewに表示！
            TextView v = (TextView)findViewById(R.id.text);
            v.setText(title + "\n\n" + telop + "\n\n" + description);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}