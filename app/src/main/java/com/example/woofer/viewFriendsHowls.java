package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class viewFriendsHowls extends AppCompatActivity{

    String storeUsername;
    private ListView lvUserWoofs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends_howls);

        String receivedText = getIntent().getStringExtra("textKey");
        //TextView textView = findViewById(R.id.getname);
        //textView.setText(receivedText);


        // Users tweets
        lvUserWoofs = findViewById(R.id.listView);
        getJSON("https://lamp.ms.wits.ac.za/home/s2596852/showFriendsTweetsP2.php?username=" + receivedText);
    }

    // Code for user's tweets
    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<UserWoofItem> userWoofItems = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String username = obj.getString("username");
            String howl = obj.getString("howl");
            String timestamp = obj.getString("created_at");
            userWoofItems.add(new UserWoofItem(username, timestamp, howl));
        }

        UserWoofAdapter userWoofAdapter = new UserWoofAdapter(this, R.layout.woof_item_layout, userWoofItems);
        lvUserWoofs.setAdapter(userWoofAdapter);
    }
}
