package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        NewAccountActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button loginButton = (Button)findViewById(R.id.newAccBackButton);
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}