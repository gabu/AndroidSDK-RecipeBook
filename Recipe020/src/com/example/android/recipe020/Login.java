package com.example.android.recipe020;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {

    // デフォルトパスワード
    private static final String DEFAULT_PASSWORD = "1234";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ホームボタンで戻ってから起動すると
        // 入力したパスワードが残って表示されてしまうので
        // 明示的に空文字をセットしてクリアする
        EditText text = (EditText)findViewById(R.id.EditText1);
        text.setText("");
    }

    public void onClick(View v) {
        // プリファレンスを取得
        SharedPreferences sp;
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        // パスワードのキーを取得
        String key = getResources()
        .getString(R.string.real_password_key);

        // パスワードを取得
        // プリファレンスに保存されていない場合は
        // DEFAULT_PASSWORDが返る
        String password = sp.getString(key, DEFAULT_PASSWORD);

        // 入力されたパスワードを取得
        EditText text = (EditText)findViewById(R.id.EditText1);
        String input = text.getText().toString();

        // パスワードがデフォルトだったら暗号化しない
        if (!DEFAULT_PASSWORD.equals(password)) {
            // 入力されたパスワードを
            // SHA-256で暗号化
            input = MyPreferenceActivity.sha256(input);
        }

        // パスワードをチェック
        if (password.equals(input)) {
            // パスワードが等しければ
            // 次の画面へ
            Intent intent = new Intent(this, Success.class);
            startActivity(intent);
        } else {
            // 等しくなければアラートダイアログを表示
            new AlertDialog.Builder(this)
            .setTitle("Can't login.")
            .setMessage("Password Error...")
            .setPositiveButton("OK", null)
            .create()
            .show();
        }
    }
}
