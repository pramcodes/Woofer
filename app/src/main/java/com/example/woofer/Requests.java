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
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
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
    JSONObject params;
    final OkHttpClient client = new OkHttpClient();

    public Requests(JSONObject obj, String link)
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
        params.keys().forEachRemaining(key -> {
            try {
                String value = params.get(key).toString();
                urlBuilder.addQueryParameter(key, value);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        String requestUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        CompletableFuture<String> futureResult = new CompletableFuture<>();

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

        params.keys().forEachRemaining(key -> {
            try {
                String value = params.get(key).toString();
                builder.add(key, value);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        CompletableFuture<String> futureResult = new CompletableFuture<>();

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

    public String addDataToDatabase() throws IOException {
        // creating an instance of OkHttpClient
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();

        params.keys().forEachRemaining(key -> {
            try {
                String value = params.get(key).toString();
                builder.add(key, value);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        // Create a Handler object associated with the main thread's Looper
        Handler handler = new Handler(Looper.getMainLooper());

        // Create a CountDownLatch to wait for the response
        CountDownLatch latch = new CountDownLatch(1);

        final AtomicReference<String> responseData = new AtomicReference<>();

        // making an asynchronous request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // method to handle errors.
                handler.post(() -> {
                    System.out.print("Fail to get response = ");
                    latch.countDown(); // Release the latch to unblock the waiting thread
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseData.set(response.body().string());
                handler.post(() -> {
                    Log.e("TAG", "RESPONSE IS " + responseData.get());
                    latch.countDown(); // Release the latch to unblock the waiting thread
                });
            }
        });

        try {
            latch.await(); // Wait for the response
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return responseData.get();
    }


}
