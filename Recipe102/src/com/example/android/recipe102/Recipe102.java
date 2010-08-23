package com.example.android.recipe102;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.http.AccessToken;
import twitter4j.http.Authorization;
import twitter4j.http.OAuthAuthorization;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class Recipe102 extends Activity {

    private static final String TAG = "Recipe102";
    static final String PREF_NAME = "Recipe102";
    static final String PREF_KEY_TOKEN = "twitter_token";
    static final String PREF_KEY_TOKEN_SECRET = "twitter_tokenSecret";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // システムプロパティにxAuthの許可をもらったアプリの
        // Consumer keyとConsumer secretをセット
        System.setProperty("twitter4j.oauth.consumerKey",
                           "hogehoge");
        System.setProperty("twitter4j.oauth.consumerSecret",
                           "hogehogehogehoge");
    }

    public void onResume() {
        super.onResume();
        // プリファレンスを取得
        SharedPreferences sp =
            getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        // tokenとtokenSecretを取得
        String token       = sp.getString(PREF_KEY_TOKEN, "");
        String tokenSecret = sp.getString(PREF_KEY_TOKEN_SECRET, "");
        // 値がなければAuthアクティビティを起動
        if ("".equals(token) || "".equals(tokenSecret)) {
            Intent intent = new Intent(this, Auth.class);
            startActivity(intent);
        }

        // twitter4jのConfigurationを取得
        Configuration conf = ConfigurationContext.getInstance();
        // AccessTokenを生成
        AccessToken accessToken = new AccessToken(token, tokenSecret);
        // OAuthAuthorizationを生成
        Authorization auth = new OAuthAuthorization(conf,
                conf.getOAuthConsumerKey(),
                conf.getOAuthConsumerSecret(),
                accessToken);
        // OAuthAuthorizationを使ってTwitterインスタンスを生成
        Twitter twitter = new TwitterFactory().getInstance(auth);

        try {
            // とりあえずテストのためTLをログ出力
            ResponseList<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
                Log.d(TAG, status.getUser().getName() + ":" + status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}