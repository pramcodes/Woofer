package com.example.woofer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WoofAdapter extends ArrayAdapter<WoofItem> {

    private Context context;
    private int layoutResourceId;
    private List<WoofItem> tweetItems;

    public WoofAdapter(Context context, int layoutResourceId, List<WoofItem> tweetItems) {
        super(context, layoutResourceId, tweetItems);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tweetItems = tweetItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView tvUserId = convertView.findViewById(R.id.tvUserId);
        TextView tvTimestamp = convertView.findViewById(R.id.tvTimestamp);
        TextView tvHowl = convertView.findViewById(R.id.tvHowl);

        WoofItem tweetItem = tweetItems.get(position);

        if (tweetItem != null) {
            tvUserId.setText(tweetItem.getUserId());
            tvTimestamp.setText(tweetItem.getTimestamp());
            tvHowl.setText(tweetItem.getHowl());
        }

        return convertView;
    }
}
