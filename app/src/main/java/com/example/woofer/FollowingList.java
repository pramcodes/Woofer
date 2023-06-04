package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FollowingList extends AppCompatActivity {
    private RecyclerView followingRecyclerView;
    private FollowingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        // Initialize RecyclerView
        followingRecyclerView = findViewById(R.id.FollowersRecyclerView);
        followingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter
        adapter = new FollowingAdapter();
        followingRecyclerView.setAdapter(adapter);

        // Populate the adapter with data
        List<User> followingList = getFollowingList(); // Replace with your own logic to fetch following user data
        adapter.setData(followingList);
    }

    // get data of following
    private List<User> getFollowingList() {
        List<User> followingList = new ArrayList<>();

        // Add following user data to the list, sort common friends to be first

        return followingList;
    }
}
