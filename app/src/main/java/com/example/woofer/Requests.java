package com.example.woofer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Requests {

    String url;
    Map<String, Object> params = new HashMap<>();


    public Requests(Map obj, String link)
    {
        url = link;
        params = obj;
    }

    //    public ArrayList<String> processJSON(String json){
//        ArrayList<String> al = new ArrayList<String>();
//        try {
//            JSONArray arr = new JSONArray(json);
//            int len = arr.length();
//            for (int j = 0; j < len; j++)
//            {
//                JSONObject obj = arr.getJSONObject(j);
//                String numP = obj.getString("NUMBER");
//                al.add(numP);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return al;
//    }

    public String getRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        // Add query parameters to the URL

        params.forEach((key, value) -> {
            String paramValue = value.toString();
            urlBuilder.addQueryParameter(key, paramValue);
        });



        String requestUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        CompletableFuture<String> futureResult = new CompletableFuture<>();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                futureResult.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new IOException("Unexpected code " + response);
                }
                String res = response.body().string();
                futureResult.complete(res);
            }
        });

        return futureResult.join();
    }

    public String postRequest() {
        FormBody.Builder builder = new FormBody.Builder();

        params.forEach((key, value) -> {
            String paramValue = value.toString();
            builder.add(key, paramValue);
        });

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        CompletableFuture<String> futureResult = new CompletableFuture<>();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                futureResult.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new IOException("Unexpected code " + response);
                }
                String res = response.body().string();
                futureResult.complete(res);
            }
        });

        return futureResult.join();
    }

    public String processJSON(String json, String FieldToReturn){
        String out = "";
        try {
            JSONObject all = new JSONObject(json);
            out = all.getString(FieldToReturn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

}
