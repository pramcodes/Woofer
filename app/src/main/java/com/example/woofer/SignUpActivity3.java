package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        SignUpActivity3.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button backButton = (Button)findViewById(R.id.backSignUp3Button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity3.this, SignUpActivity2.class);
                        startActivity(intent);
                    }
                });
                Button continueButton = (Button)findViewById(R.id.createAccountSignUp3Button);
                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity3.this, User.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}