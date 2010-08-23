package com.example.android.recipe067;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class Recipe067 extends Activity {

    private VideoView mVideoView;
    private MediaController mMediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mVideoView = (VideoView)findViewById(R.id.video_view);
        // MediaControllerを作って
        mMediaController = new MediaController(this);
        // MediaControllerセット
        mVideoView.setMediaController(mMediaController);
        // VideoViewで動画を再生する準備ができた時に
        // 呼び出されるリスナー
        mVideoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                // 3,4秒でMediaControllerが消えちゃうので
                // 常に表示させておきたいんだけど
                // showメソッドが期待通りに動作してくれない
                // durationは正しく取得できてる。
                int duration = mVideoView.getDuration();
                mMediaController.show(duration);
                // リファレンスを信じて0にしてもダメ
//                mMediaController.show(0);

                // まぁ気にせず再生スタート！
                mVideoView.start();
            }
        });
    }

    public void onPickAndVideoViewButton(View view) {
        startVideoPicker(1);
    }

    public void onPickAndPlayerButton(View view) {
        startVideoPicker(2);
    }

    public void onPlayYoutubeButton(View view) {
        // YouTubeアプリを呼び出す
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(
            Uri.parse("http://www.youtube.com/watch?v=yAZYSVr2Bhc"));
        startActivity(intent);
    }

    private void startVideoPicker(int code) {
        // インテントを作って
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // ギャラリーを呼び出す
        startActivityForResult(intent, code);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (requestCode == 1) {
                // VideoViewに動画ファイルのUriをセット
                mVideoView.setVideoURI(uri);
            } else if (requestCode == 2) {
                // プレイヤーを呼び出す
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
}