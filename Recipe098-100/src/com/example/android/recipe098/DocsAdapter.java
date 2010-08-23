package com.example.android.recipe098;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DocsAdapter extends ArrayAdapter<DocumentListEntry> {

    private LayoutInflater mLayoutInflater;

    public DocsAdapter(Context context, int textViewResourceId,
            List<DocumentListEntry> objects) {
        super(context, textViewResourceId, objects);
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        DocumentListEntry entry = getItem(position);
        View view = mLayoutInflater.inflate(R.layout.row, null);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(entry.title);
        return view;
    }
}
