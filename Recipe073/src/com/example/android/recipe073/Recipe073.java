package com.example.android.recipe073;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class Recipe073 extends Activity implements LocationListener {

    private TextView mTextView;
    private LocationManager mManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView)findViewById(R.id.text);
        // LocationManagerのインスタンスを取得
        mManager =(LocationManager)getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 現在の状況に最適なプロバイダを取得します。
        // Criteriaで細かい条件が指定できますが今回はデフォルトで
        String provider;
        provider = mManager.getBestProvider(new Criteria(), true);
        if (provider == null) {
            // 位置情報が取得できるプロバイダがありません。
            // Wifiにも3Gにも繋がっていないなど。
        }
        mManager.requestLocationUpdates(provider, 0, 0, this);
        // 最後に取得した位置情報を取得
        Location location = mManager.getLastKnownLocation(provider);
        if (location != null) onLocationChanged(location);
    }

    @Override
    public void onPause() {
        super.onPause();
        // リスナーを解除
        mManager.removeUpdates(this);
    }

    // 位置情報が変化したら呼び出される。
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        mTextView.setText("緯度=" + latitude + ", 経度=" + longitude);
    }

    // プロバイダが無効になったら呼び出される。
    public void onProviderDisabled(String provider) {
    }

    // プロバイダが有効になったら呼び出される。
    public void onProviderEnabled(String provider) {
    }

    // プロバイダの状態が変化したら呼び出される。
    public void onStatusChanged(String provider, int status,
                                Bundle extras) {
    }
}