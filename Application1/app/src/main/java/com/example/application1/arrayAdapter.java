package com.example.application1;




import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

public class arrayAdapter extends ArrayAdapter<Candidates> {
    Context context;

    public arrayAdapter(Context context, int resourceId, List<Candidates> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Candidates card_item = getItem(position);
        if (convertView == null) {                //   convert view的 复用，因为listview的展示是有限的
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(card_item.getName());
        //            Glide.with(convertView.getContext()).load(R.drawable.dog).into(image);
        if (card_item.getPhotoUrl()=="default") {
            image.setImageResource(R.drawable.dog);
        } else {
            image.setImageResource(R.drawable.home);
//            Glide.clear(image);
////            Glide.with(convertView.getContext()).load(card_item.getPhotoUrl()).into(image);
//            try {
////                URL thumb_u = card_item.getPhotoU;
////                Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
////                image.setImageDrawable(thumb_d);
//            }
//            catch (Exception e) {
//                // handle it
//            }
        }
//        switch(card_item.getPhotoUrl()) {
//            case "default":
//                Glide.with(convertView.getContext()).load(R.drawable.dog).into(image);
//                break;
//            default:
//                Glide.clear(image);
//                Glide.with(convertView.getContext()).load(card_item.getPhotoUrl()).into(image);
//                break;
//        }

        return convertView;
    }
}