package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.MutableBoolean;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FriendView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_view);

        final Button followButton = findViewById(R.id.buttonUpload);
        final TextView howlsTextView = findViewById(R.id.howlsRecyclerView);
        final MutableBoolean isFollowing = new MutableBoolean(false);
        RecyclerView howlsRecyclerView = findViewById(R.id.howlsRecyclerView);

        List<WoofItem> woofList = new ArrayList<>();//POPULATE WITH HOWLS
        int layoutResourceId = R.layout.woof_item_layout;
        WoofAdapter woofAdapter = new WoofAdapter(FriendView.this, layoutResourceId, woofList);
        howlsRecyclerView.setAdapter(woofAdapter);
        howlsRecyclerView.setAdapter(woofAdapter);
        howlsRecyclerView.setAdapter(woofAdapter);


        // Check if the user is already following or not then
        if (isFollowing.value) {
            followButton.setText("Unfollow");
            howlsTextView.setVisibility(View.VISIBLE);
        } else {
            followButton.setText("Follow");
            howlsTextView.setVisibility(View.GONE);
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowing.value) {
                    // Unfollow the user
                    // Update the button textHowl and following status accordingly
                    followButton.setText("Follow");
                    howlsTextView.setVisibility(View.GONE);
                    isFollowing.value = false;
                    //update db
                } else {
                    // Follow the user
                    // Update the button textHowl and following status accordingly
                    followButton.setText("Unfollow");
                    howlsTextView.setVisibility(View.VISIBLE);
                    isFollowing.value = true;
                    //update db
                }
               //
            }
        });



    }




}