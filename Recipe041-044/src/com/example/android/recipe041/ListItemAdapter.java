package com.example.android.recipe041;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    private LayoutInflater mInflater;

    public ListItemAdapter(Context context,
                           int rid, List<ListItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater)context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position,
                        View convertView, ViewGroup parent) {
        // データを取り出す
        ListItem item = (ListItem)getItem(position);

        // レイアウトファイルからViewを生成
        View view = mInflater.inflate(R.layout.list_item, null);

        // 画像をセット
        ImageView image;
        image = (ImageView)view.findViewById(R.id.image);
        image.setImageBitmap(item.image);

        // ユーザ名をセット
        TextView name;
        name = (TextView)view.findViewById(R.id.name);
        name.setText(item.name);

        // コメントをセット
        TextView comment;
        comment = (TextView)view.findViewById(R.id.comment);
        comment.setText(item.comment);

        return view;
    }
}
