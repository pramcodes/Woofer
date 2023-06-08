package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class userAddWoof extends AppCompatActivity {

    String storeUsername;
    ImageButton returnToUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_woof);


        //Getting username from login
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            if (username != null) {
                storeUsername=username;
            }
        }


        //Return to userAcc
        returnToUser=(ImageButton) findViewById(R.id.btnReturnToUserPage);
        returnToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(userAddWoof.this, User.class);
                startActivity(intent);
            }
        });
    }
}