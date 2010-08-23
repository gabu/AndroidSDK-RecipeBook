package com.example.android.recipe098;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.api.client.apache.ApacheHttpTransport;
import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.xml.atom.AtomParser;
import com.google.api.data.docs.v3.GoogleDocumentsList;
import com.google.api.data.docs.v3.atom.GoogleDocumentsListAtom;

public class Recipe098 extends Activity {

    private static final String TAG = "Recipe098";
    private static GoogleTransport mTransport;
    private String mAuthToken;

    public static GoogleTransport getTransport() {
        return mTransport;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // GoogleTransportを作る
        mTransport = new GoogleTransport();
        // "[company-id]-[app-name]-[app-version]"という形式で
        // アプリケーション名をセット
        mTransport.applicationName = "gabu-recipe-98";
        // バージョンをセット
        mTransport.setVersionHeader(GoogleDocumentsList.VERSION);
        // AtomParserを作る
        AtomParser parser = new AtomParser();
        // ネームスペースにGoogleDocumentsListのネームスペースをセット
        parser.namespaceDictionary =
            GoogleDocumentsListAtom.NAMESPACE_DICTIONARY;
        // GoogleTransportにAtomParserをセット
        mTransport.addParser(parser);

        // HttpTransportにApacheHttpTransportのインスタンスをセット
        // これをやっておかないとExceptionが発生します。
        HttpTransport.setLowLevelHttpTransport(
                ApacheHttpTransport.INSTANCE);

        // AccountManagerを取得
        AccountManager manager = AccountManager.get(this);
        // Googleアカウントの一覧を取得
        Account[] accounts = manager.getAccountsByType("com.google");
        // サンプルなので暫定的に1つ目を取得
        Account acount = accounts[0];
        // 認証のためのauth tokenを取得
        AccountManagerFuture<Bundle> f =
            manager.getAuthToken(acount,
                                 GoogleDocumentsList.AUTH_TOKEN_TYPE,
                                 null, this, null, null);

        try {
            Bundle b = f.getResult();
            mAuthToken = b.getString(AccountManager.KEY_AUTHTOKEN);
//            Log.d(TAG, "authToken=" + mAuthToken);
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // GoogleTransportにauth tokenをセット
        // これで認証ヘッダを自動的に付けてくれます。
        mTransport.setClientLoginToken(mAuthToken);

        // Googleドキュメントの一覧を取得するURLを作成
        String url = GoogleDocumentsList.ROOT_URL +
                        "default/private/full/-/document";
        // GoogleTransportからGETリクエストを生成
        HttpRequest request = mTransport.buildGetRequest();
        // URLをセット
        request.setUrl(url);
        try {
            // HTTPリクエストを実行してレスポンスをパース
            DocumentListFeed feed =
                request.execute().parseAs(DocumentListFeed.class);
            // DocsAdapterを生成
            DocsAdapter adapter = new DocsAdapter(
                    getApplicationContext(), 0, feed.entries);
            // ListViewにDocsAdapterをセット
            ListView listView =
                (ListView)findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(mOnItemClickListener);
        } catch (IOException e) {
            handleException(e);
        }
    }

    private OnItemClickListener mOnItemClickListener =
      new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            ListView listView = (ListView) parent;
            DocumentListEntry entry;
            entry = (DocumentListEntry)listView
                        .getItemAtPosition(position);
            Log.d(TAG, "onItemClick id=" + entry.id);
            Intent intent = new Intent(getApplicationContext(),
                                       EditActivity.class);
            intent.putExtra("docId", entry.getDocId());
            startActivity(intent);
        }
    };

    private void handleException(IOException e) {
        if (e instanceof HttpResponseException) {
          int statusCode = ((HttpResponseException) e).response.statusCode;
          if (statusCode == 401 || statusCode == 403) {
              AccountManager manager = AccountManager.get(this);
              // キャッシュを削除
              manager.invalidateAuthToken("com.google", mAuthToken);
              Toast.makeText(getApplicationContext(),
                             "キャッシュを削除しました。アプリを再起動してください。",
                             Toast.LENGTH_LONG).show();
          }
          return;
        } else {
            e.printStackTrace();
        }
    }
}