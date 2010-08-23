package com.example.recipe052;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Recipe052 extends Activity {

    private Spinner mSpinner;
    private CanvasView mCanvasView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String[] data = {
                "Point",
                "Line",
                "Rect",
                "Circle",
                "Oval",
                "Arc",
                "Path",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        mSpinner = (Spinner)findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0:
                    mCanvasView.setDrawMode(CanvasView.DRAW_POINT);
                    break;
                case 1:
                    mCanvasView.setDrawMode(CanvasView.DRAW_LINE);
                    break;
                case 2:
                    mCanvasView.setDrawMode(CanvasView.DRAW_RECT);
                    break;
                case 3:
                    mCanvasView.setDrawMode(CanvasView.DRAW_CIRCLE);
                    break;
                case 4:
                    mCanvasView.setDrawMode(CanvasView.DRAW_OVAL);
                    break;
                case 5:
                    mCanvasView.setDrawMode(CanvasView.DRAW_ARC);
                    break;
                case 6:
                    mCanvasView.setDrawMode(CanvasView.DRAW_PATH);
                    break;
                }
                mCanvasView.invalidate();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mCanvasView = (CanvasView)findViewById(R.id.canvas_view);
    }
}