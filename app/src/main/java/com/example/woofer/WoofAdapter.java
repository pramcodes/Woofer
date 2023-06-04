package com.example.woofer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WoofAdapter extends RecyclerView.Adapter<WoofAdapter.WoofViewHolder> {

    private Context context;
    private int layoutResourceId;
    private List<WoofItem> tweetItems;

    public WoofAdapter(Context context, int layoutResourceId, List<WoofItem> tweetItems) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tweetItems = tweetItems;
    }

    @NonNull
    @Override
    public WoofViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.woof_item_layout, parent, false);
        return new WoofViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WoofViewHolder holder, int position) {
        WoofItem tweetItem = tweetItems.get(position);
        holder.tvUserId.setText(tweetItem.getUserId());
        holder.tvTimestamp.setText(tweetItem.getTimestamp());
        holder.tvHowl.setText(tweetItem.getHowl());
    }

    @Override
    public int getItemCount() {
        return tweetItems.size();
    }

    public static class WoofViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId;
        TextView tvTimestamp;
        TextView tvHowl;

        public WoofViewHolder(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvHowl = itemView.findViewById(R.id.tvHowl);
        }
    }
}
