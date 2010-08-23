package com.example.android.recipe070;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

public class Recipe070 extends Activity {

    private static final String TAG = "Recipe070";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // IntentFilterを作って
        IntentFilter filter = new IntentFilter();
        // バッテリーに変化があったら通知を受け取る
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        // BroadcastReceiverを登録
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // BroadcastReceiverを解除
        unregisterReceiver(mBroadcastReceiver);
    }

    // BroadcastReceiver
    private BroadcastReceiver mBroadcastReceiver =
                                             new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // ACTION_BATTERY_CHANGEDを受け取った時だけ処理する
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                // インテントから値を取得
                int status = intent.getIntExtra("status", 0);
                int health = intent.getIntExtra("health", 0);
                int level = intent.getIntExtra("level", 0);
                int plugged = intent.getIntExtra("plugged", 0);
                int temperature =
                    intent.getIntExtra("temperature", 0);
                String technology =
                    intent.getStringExtra("technology");

                // バッテリー状態を定数で判定して
                // 意味の分かる文字列にする
                String statusStr = "";
                switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusStr = "Unknown";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusStr = "充電中";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusStr = "使用中";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    statusStr = "充電してない";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusStr = "充電完了";
                    break;
                }

                // バッテリーの健康状態を定数で判定して
                // 意味の分かる文字列にする
                String healthStr = "";
                switch (health) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthStr = "Unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthStr = "グッド";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthStr = "オーバーヒート";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthStr = "電池切れか寿命";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthStr = "過電圧";
                    break;
                case BatteryManager.
                                   BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthStr = "不特定の良くない状態";
                    break;
                }

                // 電源プラグ接続状態を定数で判定して
                // 意味の分かる文字列にする
                String pluggedStr = "";
                switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedStr = "AC電源";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedStr = "USB";
                    break;
                }

                Log.d(TAG, "status=" + statusStr);
                Log.d(TAG, "health=" + healthStr);
                Log.d(TAG, "level=" + level);
                Log.d(TAG, "plugged=" + pluggedStr);
                Log.d(TAG, "temperature=" + temperature);
                Log.d(TAG, "technology=" + technology);
            }
        }
    };
}