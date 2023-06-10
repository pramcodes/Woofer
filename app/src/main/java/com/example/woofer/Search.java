package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private ListView searchResultsListView;
    private List<User> searchResults;
    private ArrayAdapter<User> searchResultsAdapter;
    private TextView noResultsTextView;

    private ImageButton ProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        noResultsTextView = findViewById(R.id.noResultsTextView);


        searchResults = new ArrayList<>();
        searchResultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        searchResultsListView.setAdapter(searchResultsAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchEditText.getText().toString().trim(); //gets term being searched for
                performSearch(searchTerm);
            }
        });

        ProfileButton=(ImageButton)findViewById(R.id.home_button);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( Search.this,User.class );
                startActivity(intent);
            }
        });

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the clicked user's information
                User clickedUser = searchResults.get(position);

                // Start the profile activity and pass the user's information
                Intent intent = new Intent(Search.this, FriendView.class); //
                intent.putExtra("user", (Parcelable) clickedUser);
                startActivity(intent);
            }
        });

    }
    private void performSearch(String searchTerm) {
        //Searching in the database (incomplete code)
        Map<String,Object> name = new HashMap<>();
        name.put(searchTerm,"username");
        Requests search = new Requests(name,"https://lamp.ms.wits.ac.za/home/s2596852/searchUser.php");
        String Result = search.getRequest();



        // Clear previous search results
        //searchResults.clear();

        // Perform search operation and populate the searchResults list


        if (searchResults.isEmpty()) { //if no results were found
            searchResultsListView.setVisibility(View.GONE); // Hide the ListView
            noResultsTextView.setVisibility(View.VISIBLE); // Show the "No results found" message
        } else {//if results were found
            searchResultsListView.setVisibility(View.VISIBLE); // Show the ListView
            noResultsTextView.setVisibility(View.GONE); // Hide the "No results found" message
        }

        searchResultsAdapter.notifyDataSetChanged();
    }
}