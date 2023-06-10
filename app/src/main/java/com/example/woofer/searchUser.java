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
                new GetUsername().execute(searchQuery);
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

    private void AddView(JSONArray usernames) {
        cardContainer.removeAllViews();

        try {
            for (int i = 0; i < usernames.length(); i++) {
                JSONObject usernameObject = usernames.getJSONObject(i);
                String personName = usernameObject.getString("username");

                View cardView = LayoutInflater.from(this).inflate(R.layout.activity_card_fof, cardContainer, false);

                TextView friendTextView = cardView.findViewById(R.id.usernameTextView);
                Button addButton = cardView.findViewById(R.id.addButton);
                TextView fSaved = cardView.findViewById(R.id.fSaved);
                friendTextView.setText(personName);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = friendTextView.getText().toString();
                        String personName = StoreUsername; // Set the person name here
                        fSaved.setText("Friend saved!");
                        AddFriend(personName, username);
                    }
                });


                cardContainer.addView(cardView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void AddFriend(String personName, String username) {
        new AddFriendExecute().execute(personName, username);
    }
    private void RemoveVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean HostValidation(String url) {
        Pattern pattern = Pattern.compile("^https?://([^/?#]+)(?:[/?#]|$)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String hostname = matcher.group(1);
            try {
                InetAddress address = InetAddress.getByName(hostname);
                return address != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private class GetUsername extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String query = params[0];
            String url = "https://146.141.21.92/home/s2596852/searchUser.php?username=" + query;

            if (HostValidation(url)) {
                try {
                    JSONArray response = GetRequest(url);
                    return response;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(searchUser.this, "Invalid hostname", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        @Override
        protected void onPostExecute(JSONArray usernames) {
            if (usernames != null) {
                AddView(usernames);
            }
        }
    }
    private void PostRequest(String url, String parameters) throws IOException, JSONException {
        HttpURLConnection link = null;
        BufferedReader reader = null;

        try {
            URL requestUrl = new URL(url);
            link = (HttpURLConnection) requestUrl.openConnection();
            link.setRequestMethod("POST");
            link.setDoOutput(true);
            link.setDoInput(true);

            link.getOutputStream().write(parameters.getBytes());

            int responseCode = link.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(link.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (link != null) {
                link.disconnect();
            }
        }
    }
    private class AddFriendExecute extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String personName = params[0];
            String username = params[1];
            String url = "https://146.141.21.92/home/s2596852/addFriend.php";
            String parameters = "personName=" + personName + "&username=" + username;
            RemoveVerification();
            try {
                URL requestUrl = new URL(url);
                HttpsURLConnection link = (HttpsURLConnection) requestUrl.openConnection();
                link.setRequestMethod("POST");
                link.setDoOutput(true);
                OutputStream outputStream = link.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(parameters);
                writer.flush();
                writer.close();
                outputStream.close();
                int responseCode = link.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK)
                {
                }
                else
                {
                }
                link.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private JSONArray GetRequest(String url) throws IOException, JSONException {
        HttpURLConnection link = null;
        BufferedReader reader = null;
        try {
            URL requestUrl = new URL(url);
            link = (HttpURLConnection) requestUrl.openConnection();
            if (link instanceof HttpsURLConnection) {
                ((HttpsURLConnection) link).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                ((HttpsURLConnection) link).setSSLSocketFactory(sslContext.getSocketFactory());
            }
            link.setRequestMethod("GET");
            int responseCode = link.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(link.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return new JSONArray(response.toString());
            }
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (link != null) {
                link.disconnect();
            }
        }
        return null;
    }
}








