package com.example.woofer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<WoofItem> postList;

    public PostAdapter(List<WoofItem> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create and inflate the view for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_howl, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data to the views in each item
        WoofItem post = postList.get(position);
        holder.textView.setText(post.getHowl());
        // Handle other view bindings

        // Add your logic for handling like button clicks here
        holder.likeButton.setOnClickListener(v -> {
            // Handle like button click
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button likeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize your views here
            textView = itemView.findViewById(R.id.tweet_content);
            likeButton = itemView.findViewById(R.id.like_button);
        }
    }
}



