package com.example.android.recipe068;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Recipe068 extends Activity {

    private VideoView mVideoView;
    private MediaController mMediaController;
    private FrameLayout mFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // フルスクリーン表示にします
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // タイトルバーを非表示にします
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);

        mVideoView = (VideoView)findViewById(R.id.video_view);
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                showTelop();
            }
        });

        mFrameLayout = (FrameLayout)findViewById(R.id.frame_layout);
    }

    private void showTelop() {
        TextView telop = new TextView(this);
        telop.setText("TextViewをVideoViewに重ねます");
        telop.setTextColor(Color.RED);
        telop.setTextSize(40);
        telop.setTypeface(Typeface.DEFAULT, 1);
        mFrameLayout.addView(telop);
    }

    public void onPickVideoButton(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            mVideoView.setVideoURI(uri);
        }
    }
}