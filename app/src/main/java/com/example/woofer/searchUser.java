package com.example.woofer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class searchUser extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private LinearLayout cardContainer;
    private ImageButton homeButton, profileButton, nSearchButton;
    private String StoreUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        cardContainer = findViewById(R.id.cardContainer);
        homeButton = findViewById(R.id.home_button);
        profileButton = findViewById(R.id.profile_button);
        nSearchButton = findViewById(R.id.navSearchButton);

        Bundle extras = getIntent().getExtras();
        StoreUsername = extras.getString("username");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString();
                new RetrieveUsernamesTask().execute(searchQuery);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( searchUser.this,User.class );
                intent.putExtra("username", StoreUsername);
                startActivity(intent);
                finishAffinity();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( searchUser.this,viewFriendsHowls.class );
                intent.putExtra("username", StoreUsername);
                startActivity(intent);
                finishAffinity();
            }
        });
        nSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( searchUser.this,searchFoF.class );
                intent.putExtra("username", StoreUsername);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    private void addCardsToContainer(JSONArray usernames) {
        cardContainer.removeAllViews();

        try {
            for (int i = 0; i < usernames.length(); i++) {
                JSONObject usernameObject = usernames.getJSONObject(i);
                String personName = usernameObject.getString("username");

                View cardView = LayoutInflater.from(this).inflate(R.layout.activity_card_fof, cardContainer, false);

                TextView usernameTextView = cardView.findViewById(R.id.usernameTextView);
                Button addButton = cardView.findViewById(R.id.addButton);
                TextView fSaved = cardView.findViewById(R.id.fSaved);
                usernameTextView.setText(personName);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = usernameTextView.getText().toString();
                        String personName = "Leo123"; // Set the person name here
                        fSaved.setText("Friend saved!");
                        addToDifferentDatabase(personName, username);
                    }
                });
                cardContainer.addView(cardView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void addToDifferentDatabase(String personName, String username) {
        new AddToDifferentDatabaseTask().execute(personName, username);
    }

    private class RetrieveUsernamesTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String searchQuery = params[0];
            String url = "https://lamp.md.wits.ac.za/home/s2596852/searchUser.php?username=" + searchQuery;

            JSONArray response = null;
            try {
                response = makeHttpGetRequest(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(JSONArray usernames) {
            if (usernames != null) {
                addCardsToContainer(usernames);
            }
        }
    }

    private class AddToDifferentDatabaseTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String personName = params[0];
            String username = params[1];

            String url = "https://lamp.ms.wits.ac.za/home/s2596852/addFriend.php";
            String parameters = "personName=" + personName + "&username=" + username;

            try {
                URL requestUrl = new URL(url);
                HttpsURLConnection connection = (HttpsURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(parameters);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private JSONArray makeHttpGetRequest(String url) throws IOException, JSONException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return new JSONArray(response.toString());
            }
            return null;
    }
}








