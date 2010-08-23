package com.example.android.recipe102;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Auth extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
    }

    // OKボタンが押されたら呼び出される
    public void onOKButton(View view) {
        // 入力されたユーザIDとパスワードを取得
        String userID =
          ((EditText)findViewById(R.id.user_id)).getText().toString();
        String password =
         ((EditText)findViewById(R.id.password)).getText().toString();
        // とりあえずユーザIDとパスワードでTwitterインスタンスを生成
        Twitter twitter =
            new TwitterFactory().getInstance(userID, password);
        try {
            // AccessTokenを取得
            AccessToken accessToken = twitter.getOAuthAccessToken();
            // tokenとtokenSecretを取得
            String token = accessToken.getToken();
            String tokenSecret = accessToken.getTokenSecret();
            // プリファレンスのEditorを取得
            Editor e = getSharedPreferences(
                    Recipe102.PREF_NAME, MODE_PRIVATE).edit();
            // tokenとtokenSecretを書き込んで
            e.putString(Recipe102.PREF_KEY_TOKEN, token);
            e.putString(Recipe102.PREF_KEY_TOKEN_SECRET, tokenSecret);
            // 保存！
            e.commit();
            // Authアクティビティを終了
            finish();

        } catch (TwitterException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "ユーザIDかパスワードが間違っています。",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
