package com.example.woofer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity
{
    final OkHttpClient client = new OkHttpClient();
    public ArrayList<String> processJSON(String json){
        ArrayList<String> al = new ArrayList<String>();
        try {
            JSONArray arr = new JSONArray(json);
            int len = arr.length();
            for (int j = 0; j < len; j++)
            {
                JSONObject obj = arr.getJSONObject(j);
                String numP = obj.getString("username")
                        + "\n" + obj.getString("fname")
                        + "\n" + obj.getString("lname")
                        + "\n" + obj.getString("email")
                        + "\n" + obj.getString("DOB");
                al.add(numP);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return al;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2596852/users.php")
                .build();
        LinearLayout lLayout = findViewById(R.id.infoLinearLayout);
        lLayout.removeAllViews();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }
                final String resp = response.body().string();
                ArrayList<String> al = processJSON(resp);
                HomePageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        for (String x : al)
                        {
                            TextView v = new TextView(getApplicationContext());
                            v.setGravity(Gravity.CENTER);
                            v.setText(x);
                            lLayout.addView(v);
                            System.out.println(x);
                        }
                    }
                });
            }
        });


    }
}
