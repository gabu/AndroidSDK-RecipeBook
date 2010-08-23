package com.example.android.recipe026;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class Recipe026 extends Activity {

    private static final String TAG = "Recipe026";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Cursor cursor;
        cursor = managedQuery(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null);
        while (cursor.moveToNext()) {
            // コンタクトIDを取得
            String contactId;
            contactId = cursor.getString(
                cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            Log.d(TAG, "id=" + contactId);
            // 名前を取得
            String name = cursor.getString(
                cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            Log.d(TAG, "name=" + name);
            // 電話番号が登録有無を取得
            String hasPhone;
            hasPhone = cursor.getString(
                cursor.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));

            // 電話番号が登録されている場合
            if ("1".equals(hasPhone)) {
                Cursor phones;
                phones = managedQuery(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    // コンタクトIDを条件に検索
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        + " = " + contactId,
                    null, null);
                while (phones.moveToNext()) {
                    // 電話番号を取得
                    String phoneNumber;
                    phoneNumber = phones.getString(
                        phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "phoneNumber=" + phoneNumber);
                }
                phones.close();
            }

            Cursor emails;
            emails = managedQuery(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                // コンタクトIDを条件に検索
                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                    + " = " + contactId,
                null, null);
            while (emails.moveToNext()) {
                // メールアドレスを取得
                String email;
                email = emails.getString(
                    emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA1));
                Log.d(TAG, "email=" + email);
            }
            emails.close();
        }
        cursor.close();
    }
}