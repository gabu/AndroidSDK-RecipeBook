package com.example.android.recipe016;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

public class Recipe016 extends Activity {

    private static final String TAG = "Recipe016";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

     // テストデータ
        String xml = "<user id=\"1\" name=\"gabu\"><age>25</age></user>";

        // パーサのインスタンスを取得
        XmlPullParser parser = Xml.newPullParser();

        try {
            // パーサにXMLをセット
            parser.setInput(new StringReader(xml));

            // イベントタイプを初期化
            int eventType = parser.getEventType();

            // イベントタイプがEND_DOCUMENTになるまでループ
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    // アトリビュートの数を取得
                    int count = parser.getAttributeCount();

                    // アトリビュートの数だけループ
                    for (int i=0; i<count; i++) {

                        // アトリビュートの名前を取得
                        String name = parser.getAttributeName(i);
                        Log.d(TAG, "name=" + name);

                        // アトリビュートの値を取得
                        String value = parser.getAttributeValue(i);
                        Log.d(TAG, "value=" + value);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
                case XmlPullParser.TEXT:
                    // START_TAG -> TEXT とイベントが発生する
                    // 今回の例だと<age>25</age>の25の部分が現在の位置
                    // この状態でgetTextメソッドでテキストを取得する
                    Log.d(TAG, "text=" + parser.getText());
                    break;
                default:
                    break;
                }
                // パーサを次のイベントまで進める
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            // when setInput()
            e.printStackTrace();
        } catch (IOException e) {
            // when next()
            e.printStackTrace();
        }
    }
}