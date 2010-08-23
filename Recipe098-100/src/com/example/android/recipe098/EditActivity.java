package com.example.android.recipe098;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.data.docs.v3.GoogleDocumentsList;

public class EditActivity extends Activity {

    private static GoogleTransport mTransport;
    private EditText mEditText;
    private String mDocId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        mEditText = (EditText)findViewById(R.id.edit_text);

        // インテントからIDを取得
        Intent intent = getIntent();
        mDocId = intent.getStringExtra("docId");

        // GoogleTransportを取得して
        mTransport = Recipe098.getTransport();
        // URLを生成して
        String url = GoogleDocumentsList.ROOT_URL +
              "download/documents/Export?docId=" + mDocId +
              "&exportFormat=txt";
        // GoogleTransportからGETリクエストを生成
        HttpRequest request = mTransport.buildGetRequest();
        // URLをセット
        request.setUrl(url);
        HttpResponse response;
        try {
            // HTTPリクエストを実行！
            response = request.execute();
            // HTTPレスポンスをStringにパースします。
            // Googleドキュメントの文書をtxtでダウンロードすると
            // 改行コードが"\r\n"なので"\n"に置換します。
            mEditText.setText(
                response.parseAsString().replaceAll("\r\n", "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSaveButtonClick(View view) {
        // GoogleTransportからPUTリクエストを生成
        HttpRequest request = mTransport.buildPutRequest();
        // URLを生成して
        String url = GoogleDocumentsList.ROOT_URL +
            "default/media/document%3A" + mDocId;
        // URLをセット
        request.setUrl(url);
        // EditTextの内容でPlainTextContentを作って
        PlainTextContent content = new PlainTextContent(
                mEditText.getText().toString());
        // HttpRequestにPlainTextContentをセット
        request.content = content;
        // HttpRequestのヘッダーのifMatchに"*"をセット
        request.headers.ifMatch = "*";
        try {
            // HTTPリクエストを実行！
            request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
