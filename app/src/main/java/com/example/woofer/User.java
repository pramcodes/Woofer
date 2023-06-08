package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class User extends AppCompatActivity implements View.OnClickListener{
    String storeUsername;
    String id;
    EditText editTextId;
    TextView etFollowersCount;
    TextView etFollowingCount;

    public static final String UPLOAD_URL = "https://lamp.ms.wits.ac.za/home/s2596852/uploadidk.php";
    //public static final String UPLOAD_URL = "https://lamp.ms.wits.ac.za/home/s2572730/uploadidk.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Button buttonUpload;
    private Button buttonView;
    private ImageButton buttonToHowl;
    private ImageButton toLogin;

    private ImageView ivChooseShowPic;

    private Bitmap bitmap;

    private Uri filePath;

    private ImageButton logOutImageButton;
    private Button buttonLogOut;
    private boolean btnVisibility=false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Code for logout p1
        logOutImageButton=findViewById(R.id.imageButtonReturnToLogin);
        buttonLogOut=findViewById(R.id.buttonLogout);

        //Moving to howls page
        buttonToHowl=(ImageButton) findViewById(R.id.buttonUserAddHowl);
        buttonToHowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(User.this, userAddWoof.class);
                startActivity(intent);
            }
        });

        //Moving back to login
/*        toLogin=(ImageButton) findViewById(R.id.imageButtonReturnToLogin);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(User.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/




        editTextId = findViewById(R.id.etID);

        ivChooseShowPic = findViewById(R.id.profilePic);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonView = findViewById(R.id.buttonView);

        imageView = findViewById(R.id.profilePic);

        //Getting username from login page and displaying
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            if (username != null) {
                editTextId.setText(username);
                storeUsername=username;
            }
        }

        etFollowersCount =findViewById(R.id.followersCount);
        etFollowingCount =findViewById(R.id.followingCount);



        ivChooseShowPic.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        ViewImage();
        getFollowingCount();
        getFollowerCount();
    }

    //When you click on the drawable you can pick a profile picture
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivChooseShowPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(User.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                id = editTextId.getText().toString().trim();

                HashMap<String,String> data = new HashMap<>();
                data.put("id", id);
                data.put(UPLOAD_KEY, uploadImage);

                //String result = rh.sendPostRequest(UPLOAD_URL,data);
                String result = rh.sendPostRequest(UPLOAD_URL, data, id);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

  /*  private void viewImage() {
        //EditText editTextId;
        ImageView imageView;

        //editTextId = findViewById(R.id.etID);
        imageView = findViewById(R.id.profilePic);

        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String, Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(User.this, "Uploading...", null, true, true);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "https://lamp.ms.wits.ac.za/home/s2596852/getImage3.php?username=" + id;
                URL url;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.drawable.baseline_person_24);
                }
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }*/


    @Override
    public void onClick(View v) {
        if (v == ivChooseShowPic) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
        if(v == buttonView){
            ViewImage();
        }
    }

    private void ViewImage(){
        //RequestHandler requestHandler;
        //requestHandler=new RequestHandler();
        buttonView.setOnClickListener(this);
        getImage();

    }

    private void getImage() {
        id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void,Bitmap>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(User.this, "Uploading...", null,true,true);
            }

            /* @Override
             protected void onPostExecute(Bitmap b) {
                 super.onPostExecute(b);
                 loading.dismiss();
                 ivTest.setImageBitmap(b);
             }*/
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                if (bitmap != null) {
                    ivChooseShowPic.setImageBitmap(bitmap);
                } else {
                    //Toast.makeText(User.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.baseline_person_24);
                }
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "https://lamp.ms.wits.ac.za/home/s2596852/getImageidk.php?id="+id;
                //String add = "https://lamp.ms.wits.ac.za/home/s2572730/getImageidk.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }



    private void getFollowingCount() {
        //String username = editTextId.getText().toString().trim();

        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2596852/countFollowing.php?username=" + storeUsername;

        Request request = new Request.Builder()
                .url(url)
                .build();

        class GetFollowingCountTask extends AsyncTask<Void, Void, Integer> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(User.this, "Loading...", null, true, true);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                try {

                    Response response = client.newCall(request).execute();


                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        return Integer.parseInt(responseBody);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Integer followingCount) {
                super.onPostExecute(followingCount);
                loading.dismiss();

                if (followingCount != null) {
                    etFollowingCount.setText("Following: " + String.valueOf(followingCount));
                } else {
                    Toast.makeText(User.this, "Failed to retrieve following count.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetFollowingCountTask task = new GetFollowingCountTask();
        task.execute();
    }



    private void getFollowerCount() {
        //String username = editTextId.getText().toString().trim();

        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2596852/countFollowers.php?username=" + storeUsername;

        Request request = new Request.Builder()
                .url(url)
                .build();

        class GetFollowersCountTask extends AsyncTask<Void, Void, Integer> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(User.this, "Loading...", null, true, true);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string().trim();
                        return Integer.parseInt(responseBody);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer followerCount) {
                super.onPostExecute(followerCount);
                loading.dismiss();

                if (followerCount != null) {
                    etFollowersCount.setText("Followers: " + String.valueOf(followerCount));
                } else {
                    Toast.makeText(User.this, "Failed to retrieve follower count.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetFollowersCountTask task = new GetFollowersCountTask();
        task.execute();
    }

    //Code for logout button P2
    public void onImageButtonClick(View view) {
        if (btnVisibility) {
            buttonLogOut.setVisibility(View.GONE);
            btnVisibility = false;
        } else {
            buttonLogOut.setVisibility(View.VISIBLE);
            buttonLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(User.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            btnVisibility = true;
        }
    }
}