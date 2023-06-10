package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class userAddWoof extends AppCompatActivity {

    String storeUsername;
    ImageButton returnToUser;

    EditText text;

    Button AddHowl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_woof);


        //Getting username from user
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
                intent.putExtra("username",storeUsername);
                startActivity(intent);
            }
        });

        AddHowl = (Button)findViewById(R.id.btnWoof);
        AddHowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = (EditText) findViewById(R.id.etWoofContent);
                String Howl = text.getText().toString();

                Map<String,Object> Post = new HashMap<>();
                Post.put("username",storeUsername);
                Post.put("howl",Howl);
                Requests Req = new Requests(Post,"https://lamp.ms.wits.ac.za/home/s2596852/addTweet.php");

                String text = Req.postRequest();
                Toast toast = Toast.makeText(getApplicationContext() , text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}