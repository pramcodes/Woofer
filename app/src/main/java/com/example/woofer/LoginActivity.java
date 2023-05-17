package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button signInButton = (Button)findViewById(R.id.signInLoginButton);
                signInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}