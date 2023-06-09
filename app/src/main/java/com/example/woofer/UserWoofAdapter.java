package com.example.woofer;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserWoofAdapter extends ArrayAdapter<UserWoofItem> {

    private Context context;
    private int layoutResourceId;
    private List<UserWoofItem> userWoofItems;


    public UserWoofAdapter(Context context, int layoutResourceId, List<UserWoofItem> userWoofItems) {
        super(context, layoutResourceId, userWoofItems);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.userWoofItems = userWoofItems;
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

        UserWoofItem userWoofItem = userWoofItems.get(position);

        if (userWoofItem != null) {
            tvUserId.setText(userWoofItem.getName());  // Display the name instead of the userId
            tvTimestamp.setText(userWoofItem.getTimestamp());
            highlightHashtags(tvHowl, userWoofItem.getHowl()); // Apply hashtag highlighting
        }

        return convertView;
    }

    private void highlightHashtags(TextView textView, String text) {
        SpannableString spannableString = new SpannableString(text);

        // Find hashtags using regex pattern
        Pattern hashtagPattern = Pattern.compile("#\\w+");
        Matcher matcher = hashtagPattern.matcher(text);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();


            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannableString);
    }
}
