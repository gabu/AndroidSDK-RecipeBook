package com.example.android.recipe019;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.Log;

public class MyPreferenceActivity extends PreferenceActivity {
    private Context mContext;
    private String mInputPasswordKey;
    private String mRealPasswordKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        mContext = this;
        Resources r = getResources();
        mInputPasswordKey = r.getString(R.string.input_password_key);
        mRealPasswordKey = r.getString(R.string.real_password_key);

        // パスワード入力用のプリファレンスを取得
        Preference password = findPreference(mInputPasswordKey);

        // 変更を検知するリスナーをセット
        password.setOnPreferenceChangeListener(
            new OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                // 本来のパスワードには空文字をセット
                ((EditTextPreference)preference).setText("");

                // SharedPreferencesを取得
                SharedPreferences sp;
                sp = PreferenceManager
                     .getDefaultSharedPreferences(mContext);

                // 編集のためEditorを取得
                Editor editor = sp.edit();

                // key: real_password_key
                // value: SHA-1値
                // newValueに入力された値が入っています。
                editor.putString(mRealPasswordKey,
                                 sha256(newValue.toString()));

                // 保存
                editor.commit();
                return false;
            }
        });
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, digest);
            String sha = number.toString(16);

            // 先頭0埋め
            while (sha.length() < 64)
                sha = "0" + sha;

            return sha;
        } catch (NoSuchAlgorithmException e) {
            Log.e("sha256", e.getMessage());
            return null;
        }
    }
}
