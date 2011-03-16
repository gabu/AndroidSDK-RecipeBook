package com.example.android.recipe101;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.apache.ApacheHttpTransport;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.xml.atom.AtomParser;

public class Recipe101 extends Activity {
    private static final String GOOGLE_CAL_API_URL = "https://www.google.com/calendar/feeds/";
    private static final String CAL_AUTH_TOKEN_TYPE = "cl";
    private static final String TAG = "Recipe101";
    private static HttpTransport mTransport;
    private String mAuthToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTransport = GoogleTransport.create();
        GoogleHeaders headers = (GoogleHeaders) mTransport.defaultHeaders;
        // "[company-id]-[app-name]-[app-version]"という形式で
        // アプリケーション名をセット
        headers.setApplicationName("gabu-recipe-101");
        // バージョンをセット
        headers.gdataVersion = "2";
        // AtomParserを作る
        AtomParser parser = new AtomParser();
        // GoogleCalendarのネームスペースをセット
        parser.namespaceDictionary = Namespace.DICTIONARY;
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
                                 CAL_AUTH_TOKEN_TYPE,
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
        ((GoogleHeaders) mTransport.defaultHeaders).setGoogleLogin(mAuthToken);
        // GoogleTransportからGETリクエストを生成
        HttpRequest request = mTransport.buildGetRequest();

        // Googleカレンダーの一覧を取得するURLを作成
        // 共有しているカレンダーも含む
//        String url = GOOGLE_CAL_API_URL + "default/allcalendars/full";
        // 自分がオーナーのカレンダーのみ
        String url = GOOGLE_CAL_API_URL + "default/owncalendars/full";

        // URLをセット
        request.setUrl(url);
        try {
            // HTTPリクエストを実行してレスポンスをパース
            CalendarFeed feed =
                request.execute().parseAs(CalendarFeed.class);
            for (CalendarEntry entry : feed.entries) {
                Log.d(TAG, entry.title + ", " + entry.id);
            }
            // サンプルのためカレンダーの1つ目を取得
            CalendarEntry entry = feed.entries.get(0);
            // このuserIDがカレンダーを一意に特定するIDになります。
            String userID = entry.getUserID();

            // 何も指定せずに予定の一覧を取得
            url = GOOGLE_CAL_API_URL + userID + "/private/full";
            debug(request, url);

            // 2010年6月の予定の一覧を取得
//            url = createMonthlyUrl(userID, 2010, 6);
//            debug(request, url);

            // 2010年6月30日の予定の一覧
//            url = createDailyUrl(userID, 2010, 6, 30);
//            debug(request, url);

        } catch (IOException e) {
            handleException(e);
        }
    }

    // 指定された日の予定を取得するURLを生成します。
    private String createDailyUrl(String userID,
                                  int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        Time fromTime = new Time();
        fromTime.set(cal.getTimeInMillis());
        // RFC 3339フォーマットにします。
        String from =  fromTime.format3339(true);

        cal.add(Calendar.DATE, 1);

        Time toTime = new Time();
        toTime.set(cal.getTimeInMillis());
        // RFC 3339フォーマットにします。
        String to =  toTime.format3339(true);

        return createUrl(userID, from, to);
    }

    // 指定された月の予定を取得するURLを生成します。
    private String createMonthlyUrl(String userID,
                                    int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Time fromTime = new Time();
        fromTime.set(cal.getTimeInMillis());
        // RFC 3339フォーマットにします。
        String from =  fromTime.format3339(true);

        cal.add(Calendar.MONTH, 1);

        Time toTime = new Time();
        toTime.set(cal.getTimeInMillis());
        // RFC 3339フォーマットにします。
        String to =  toTime.format3339(true);

        return createUrl(userID, from, to);
    }

    private String createUrl(String userID, String from, String to) {
        return GOOGLE_CAL_API_URL
                + userID + "/private/full"
                + "?start-min=" + URLEncoder.encode(from)
                + "&start-max=" + URLEncoder.encode(to)
                + "&orderby=starttime"
                + "&sortorder=ascending"
                + "&singleevents=true";
    }

    // リクエストを実行して、結果をログ出力します。
    private void debug(HttpRequest request, String url) {
        try {
            Log.d(TAG, url);
            request.setUrl(url);
            HttpResponse response = RedirectHandler.execute(request);
            CalendarEventFeed eventFeed =
                response.parseAs(CalendarEventFeed.class);
            for (CalendarEventEntry event : eventFeed.entries) {
                Log.d(TAG, event.title);
                if (event.when != null) {
                    Log.d(TAG, event.when.startTime);
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    private void handleException(IOException e) {
        if (e instanceof HttpResponseException) {
          int statusCode = ((HttpResponseException) e).response.statusCode;
          System.out.println(statusCode);
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