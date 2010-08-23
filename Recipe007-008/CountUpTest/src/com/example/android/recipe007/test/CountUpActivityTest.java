package com.example.android.recipe007.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.recipe007.R;
import com.example.android.recipe007.CountUpActivity;

public class CountUpActivityTest extends ActivityInstrumentationTestCase2<CountUpActivity> {

    private TextView mTextView;
    private Button mButton;

    public CountUpActivityTest() {
        super("com.example.android.recipe", CountUpActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CountUpActivity activity = getActivity();
        mTextView = (TextView)activity.findViewById(R.id.TextView01);
        mButton = (Button)activity.findViewById(R.id.Button01);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPlus() throws Exception {
        CountUpActivity activity = getActivity();
        assertEquals(2, activity.plus(1, 1));
    }

    public void testButtonClick() throws Exception {
        assertEquals("0", mTextView.getText().toString());
        buttonClick();
        assertEquals("1", mTextView.getText().toString());
        buttonClick();
        assertEquals("2", mTextView.getText().toString());
    }

    private void buttonClick() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                mButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
    }
}
