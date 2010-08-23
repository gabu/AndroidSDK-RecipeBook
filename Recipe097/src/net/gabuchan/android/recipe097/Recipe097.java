package net.gabuchan.android.recipe097;

import android.app.Activity;
import android.os.Bundle;

import com.admob.android.ads.AdManager;

public class Recipe097 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        AdManager.setTestDevices( new String[] {
            AdManager.TEST_EMULATOR,
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}