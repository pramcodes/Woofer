package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        SignUpActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button backButton = (Button)findViewById(R.id.backSignUp2Button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity2.this, SignUpActivity1.class);
                        startActivity(intent);
                    }
                });
                Button continueButton = (Button)findViewById(R.id.continueSignUp2Button);
                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity2.this, SignUpActivity3.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}