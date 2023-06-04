package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private ListView searchResultsListView;
    private List<User> searchResults;
    private ArrayAdapter<User> searchResultsAdapter;
    private TextView noResultsTextView;


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
}
    private void performSearch(String searchTerm) {
        // Clear previous search results
        searchResults.clear();

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