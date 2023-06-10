package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //JSON processing method
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //php login web
        OkHttpClient client = new OkHttpClient();
        EditText Uname = (EditText) findViewById(R.id.uNameLoginView) ;
        TextInputEditText Upasswrd = (TextInputEditText) findViewById(R.id.Password_Text) ;

        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button LoginButton = (Button) findViewById(R.id.loginLoginButton) ;
                LoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //stuff for when login button is clicked
                        String username = Uname.getText().toString();
                        String passwrd = Upasswrd.getText().toString();
                        //insert validation here on username and passwrd
/*                        JSONObject objName = new JSONObject();
                        try {
                            objName.put("username",username);
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "could not make JSON objName", Toast.LENGTH_SHORT).show();
                        }
                        Requests CheckPsswrd = new Requests(objName, "https://lamp.ms.wits.ac.za/home/s2596852/user.php");
                        String pswrd = CheckPsswrd.getRequest();
                        //compares user's entered password with password stored on the database
                        if ((pswrd.equals(passwrd)) && (pswrd != "")){
                            Intent intent = new Intent(LoginActivity.this, User.class);
                            startActivity(intent);
                        }
                        else {
                            CharSequence textHowl = "Incorrect password or username";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(getApplicationContext() , textHowl, duration);
                            toast.show();
                        }*/


                        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2596852/users.php").newBuilder();
                        urlBuilder.addQueryParameter("username",username);
                        String url = urlBuilder.build().toString();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                }else {
                                    final String resp = response.body().string();

                                    LoginActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String pswrd = processJSON(resp,"password");
                                            //compares user's entered password with password stored on the database
                                            if ((pswrd.equals(passwrd)) && (pswrd != "")){
                                                Intent intent = new Intent(LoginActivity.this, User.class);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                            }
                                            else {
                                                CharSequence text = "Incorrect password or username";
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(getApplicationContext() , text, duration);
                                                toast.show();
                                            }

                                        }
                                    });
                                }
                            }
                        });
                    }
                });

                Button signUpButton = (Button)findViewById(R.id.signUpLoginButton);
                signUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity1.class);
                        startActivity(intent);
                    }
                });


                ImageButton exitButton = (ImageButton) findViewById(R.id.loginExitButton);
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        finishAffinity();
                        System.exit(0);
                    }
                });
            }
        });


    }
}