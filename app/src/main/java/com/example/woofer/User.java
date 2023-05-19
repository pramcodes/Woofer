package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        User.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button infoButton = (Button)findViewById(R.id.userInfoButton);
                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(User.this, HomePageActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}