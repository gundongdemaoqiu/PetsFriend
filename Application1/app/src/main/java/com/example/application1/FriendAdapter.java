package com.example.application1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendAdapter extends ArrayAdapter {
    private List<String> itemNames;
    private Integer[] imageid;
    private Activity context;

    public FriendAdapter(Activity context, List<String> itemNames, Integer[] imageid) {
        super(context, R.layout.friend_row_item, itemNames);
        this.context = context;
        this.itemNames = itemNames;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.friend_row_item, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.itemNames);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.itemImages);

//        textViewCountry.setText(itemNames.get(position));
        textViewCountry.setText(itemNames.get(position));
        imageFlag.setImageResource(imageid[0]);    // 暂时只有一个好友，限定一张图片，这里不能来图片也应该list从数据库拿去
//        imageFlag.setImageResource(imageid[position]);
        return  row;
    }
}