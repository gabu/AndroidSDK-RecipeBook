package com.example.android.recipe095;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class Recipe095 extends Activity implements Runnable {

    private Uri mUri;
    private ProgressDialog mProgress;
    // ハンドラー
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // プログレスダイアログを閉じます。
            mProgress.dismiss();
            // アクティビティを終了します。
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // インテントを取得
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // インテントに何も値がセットされてなければ何もしません。
        if (bundle == null) return;
        // インテントにEXTRA_STREAMがセットされてなければ何もしません。
        if (!bundle.containsKey(Intent.EXTRA_STREAM)) return;

        // 選択された写真のUriを取得
        mUri = (Uri) bundle.getParcelable(Intent.EXTRA_STREAM);
        // ImageViewに表示
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageURI(mUri);

        // プログレスダイアログを表示
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Uploading...");
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.show();

        // アップロードを開始
        Thread thread = new Thread(this);
        thread.start();
    }

    // 指定されたUriの写真をTumblrにアップロードします。
    private void uploadForTumblr(Uri uri) {
        // HTTPクライアントを作って
        HttpClient client = new DefaultHttpClient();
        // POST先のURLを指定してPOSTオブジェクトを作って
        HttpPost post =
            new HttpPost("http://www.tumblr.com/api/write");
        // パラメータを作って
        MultipartEntity entity = new MultipartEntity();
        try {
            // Thumblrに登録したメールアドレス
            entity.addPart("email",
                    new StringBody("hoge@example.com"));
            // Thumblrに登録したパスワード
            entity.addPart("password", new StringBody("1234"));
            // 投稿する種類。今回は写真なのでphoto
            entity.addPart("type", new StringBody("photo"));
            // 写真データ
            entity.addPart("data", new InputStreamBody(
                            getContentResolver().openInputStream(uri),
                            "filename"));
            // POSTオブジェクトにパラメータをセット
            post.setEntity(entity);
            // POSTリクエストを実行
            client.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // アップロードを実行
        uploadForTumblr(mUri);
        // ハンドラに終了を通知
        mHandler.sendEmptyMessage(0);
    }
}
