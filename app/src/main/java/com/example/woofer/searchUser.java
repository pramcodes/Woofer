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
                        String personName = StoreUsername; // Set the person name here
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
    private void disableSSLCertificateVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isHostnameValid(String url) {
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

    private class RetrieveUsernamesTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String searchQuery = params[0];
            String url = "https://146.141.21.92/home/s2596852/searchUser.php?username=" + searchQuery;

            if (isHostnameValid(url)) {
                try {
                    JSONArray response = makeHttpGetRequest(url);
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
                addCardsToContainer(usernames);
            }
        }
    }
    private void makeHttpPostRequest(String url, String parameters) throws IOException, JSONException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.getOutputStream().write(parameters.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private class AddToDifferentDatabaseTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String personName = params[0];
            String username = params[1];

            String url = "https://146.141.21.92/home/s2596852/addFriend.php";
            String parameters = "personName=" + personName + "&username=" + username;

            disableSSLCertificateVerification();

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

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

                }
                else
                {

                }
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

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();

            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
            }

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

            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}








