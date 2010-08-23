package com.example.android.recipe029;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class Recipe029 extends Activity {

    private ProgressDialog mProgressDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onSpinnerClick(View view) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("通信中");
        mProgressDialog.setMessage("しばらくお待ちください");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        Thread thread = new Thread(new MockForSpinner());
        thread.start();
    }

    public void onHorizontalClick(View view) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("通信中");
        mProgressDialog.setMessage("しばらくお待ちください");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();

        Thread thread = new Thread(new MockForHorizontal());
        thread.start();
    }

    private class MockForSpinner implements Runnable {

        public void run() {
            try {
                // 時間のかかる処理のつもりで
                // 5秒スリープ
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ハンドラにメッセージを通知
            mHandler.sendEmptyMessage(0);
        }
    }

    private class MockForHorizontal implements Runnable {

        public void run() {
            try {
                // 進捗100％の値を5として
                mProgressDialog.setMax(5);
                for (int i = 1; i <= 5; i++) {
                    // 1から5までをセット
                    mProgressDialog.setProgress(i);
                    // サンプルのため1秒スリープ
                    Thread.sleep(1 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(0);
        }
    }
}