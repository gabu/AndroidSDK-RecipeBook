package com.example.android.recipe094;

import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.music.IMediaPlaybackService;

public class Recipe094 extends Activity {
    private static final String TAG = "Recipe094";

    private ServiceConnection mConn;
    private IMediaPlaybackService mService;
    private MusicBroadcastReceiver mReceiver;

    private TextView mTrackTextView;
    private TextView mArtistTextView;
    private TextView mAlbumTextView;
    private TextView mPlayingTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTrackTextView = (TextView)findViewById(R.id.track);
        mArtistTextView = (TextView)findViewById(R.id.artist);
        mAlbumTextView = (TextView)findViewById(R.id.album);
        mPlayingTextView = (TextView)findViewById(R.id.playing);
    }

    @Override
    public void onResume() {
        super.onResume();
        // インテントを作って
        Intent intent = new Intent();
        intent.setClassName("com.android.music",
                            "com.android.music.MediaPlaybackService");
        // サービスコネクションを作って
        mConn = new MediaPlayerServiceConnection();
        // バインドします。
        bindService(intent, mConn, 0);

        // インテントフィルターを作って
        IntentFilter filter = new IntentFilter();
        // 以下のアクションに反応します。
        filter.addAction("com.android.music.metachanged");
        filter.addAction("com.android.music.playbackcomplete");
        filter.addAction("com.android.music.playstatechanged");
        // ブロードキャストレシーバを作って
        mReceiver = new MusicBroadcastReceiver();
        // 登録します。
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // ブロードキャストレシーバを解除します。
        unregisterReceiver(mReceiver);
        // サービスへのバインドを解除します。
        unbindService(mConn);
    }

    // サービスコネクションです。
    private class MediaPlayerServiceConnection
        implements ServiceConnection {

        // サービスに接続できた時に呼び出されます。
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            // IMediaPlaybackServiceのインスタンスを取得
           mService = IMediaPlaybackService.Stub.asInterface(service);
            // 表示内容を更新する。
            updateMusicInfo();
        }
        // サービスとの接続が切れた時に呼び出されます。
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    // ブロードキャストレシーバです。
    private class MusicBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // アクション名をログ出力
            Log.d(TAG, "recieved action: " + intent.getAction());
            // インテントにセットされた値を確認してみます。
            // 以下はデバッグ用です。
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // キーの一覧をログ出力
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    Log.d(TAG, "key: " + key);
                }
                // どうやら以下が取得できそうなのでログ出力
                // 曲名
                String track = bundle.getString("track");
                Log.d(TAG, "track: " + track);
                // アーティスト名
                String artist = bundle.getString("artist");
                Log.d(TAG, "artist: " + artist);
                // アルバム名
                String album = bundle.getString("album");
                Log.d(TAG, "album: " + album);
            }
            // 表示内容を更新する。
            updateMusicInfo();
        }
    }

    // 画面の表示内容を更新します。
    private void updateMusicInfo() {
        if (mService == null) return;
        try {
            // 曲名を取得してセット
            mTrackTextView.setText(
                    "Track: " + mService.getTrackName());
            // アーティスト名を取得してセット
            mArtistTextView.setText(
                    "Artist: " + mService.getArtistName());
            // アルバム名を取得してセット
            mAlbumTextView.setText(
                    "Album: " + mService.getAlbumName());
            // 再生中かどうかを判定してセット
            if (mService.isPlaying()) {
                mPlayingTextView.setText("Playing");
            } else {
                mPlayingTextView.setText("Not playing");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void onPrevButton(View view) {
        if (mService != null) {
            try {
                // 前の曲へ
                mService.prev();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStopButton(View view) {
        if (mService != null) {
            try {
                // 停止
                mService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPauseButton(View view) {
        if (mService != null) {
            try {
                // 一時停止
                mService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPlayButton(View view) {
        if (mService != null) {
            try {
                // 再生
                mService.play();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNextButton(View view) {
        if (mService != null) {
            try {
                // 次の曲へ
                mService.next();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}