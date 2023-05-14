package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button loginButton = (Button)findViewById(R.id.loginButton);
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                Button nAccButton = (Button)findViewById(R.id.newAccButton);
                nAccButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, NewAccountActivity.class);
                        startActivity(intent);
                    }
                });
                Button exitButton = (Button) findViewById(R.id.exitButton);
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