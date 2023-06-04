package com.example.woofer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private List<User> followingList;
    private UserDetails userDetails;

    public FollowingAdapter(UserDetails userDetails) {
        followingList = new ArrayList<>();
        this.userDetails = userDetails;
    }

    public FollowingAdapter() {

    }

    public void setData(List<User> followingList) {
        this.followingList = followingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_following_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = followingList.get(position);
        holder.usernameTextView.setText(userDetails.getUsername());

        // Set the appropriate text for the follow button
        //get whether user is following or not
       boolean isFollowing = false;
        if (isFollowing) {
            holder.followButton.setText("Unfollow");
        } else {
            holder.followButton.setText("Follow");
        }

        // Set click listener for the follow button
        holder.followButton.setOnClickListener(v -> {
            // Update the follow status of the user
            boolean newFollowStatus = !isFollowing;


            // Update the text of the follow button
            if (newFollowStatus) {
                holder.followButton.setText("Unfollow");
            } else {
                holder.followButton.setText("Follow");
            }
        });
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public Button followButton;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }
}
