package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class SignUpActivity1 extends AppCompatActivity {

    private static final String TAG = "SignUpActivity1";
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    EditText username, fName, lName, email;
    Button confirm;
    TextView date;
    TextInputLayout password, confirmPassword;
    private boolean isBirthdateSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_up1);
        setContentView(R.layout.activity_sign_in_test);

        //for datePicker
        displayDate= (TextView) findViewById(R.id.textViewDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int year= calendar.get(Calendar.YEAR);
                int month= calendar.get(Calendar.MONTH);
                int day= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker= new DatePickerDialog(
                        SignUpActivity1.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                String date=  month + "/" + day + "/" + year;
                displayDate.setText(date);
            }
        };
        //Return to login page
        Button backButton = (Button)findViewById(R.id.newAccBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity1.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Validation checks
        username=findViewById(R.id.uNameNewAccView);
        fName=findViewById(R.id.fNameNewAccView);
        lName=findViewById(R.id.lNameNewAccView);
        email=findViewById(R.id.editTextEmail);
        confirm= findViewById(R.id.newAccConfirmButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.length()==0){
                    username.setError("Enter username");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                else if (fName.length()==0) {
                    fName.setError("Enter first name");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                else if (lName.length()==0) {
                    lName.setError("Enter last name");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                else if (email.length()==0) {
                    email.setError("Enter email");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                /*
                 else if (!isBirthdateSelected) {
                    displayDate.setError("Select your birthdate");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }

                 else if (password.getEditText().length() == 0) {
                    password.setError("Enter password");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                else if (confirmPassword.getEditText().length() == 0) {
                    confirmPassword.setError("Enter password");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                 */

                else {
                    Toast.makeText(SignUpActivity1.this, "Works :D", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

//Liam's code
/*
SignUpActivity1.this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Button backButton = (Button)findViewById(R.id.backSignUp1Button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity1.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                Button continueButton = (Button)findViewById(R.id.continueSignUp1Button);
                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity1.this, SignUpActivity2.class);
                        startActivity(intent);
                    }
                });

            }
        });
 */