package com.example.woofer;
import com.example.woofer.PostAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class likesPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<WoofItem> postList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes_page);

        recyclerView = findViewById(R.id.likes_recycler_view);
        postAdapter = new PostAdapter(postList);
        postList = new ArrayList<>();
        postList.add(new WoofItem("uoioijolio"));
        postList.add(new WoofItem("111111112"));

        recyclerView = findViewById(R.id.likes_recycler_view);
        postAdapter = new PostAdapter(postList);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);
    }}