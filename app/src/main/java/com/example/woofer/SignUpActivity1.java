package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SignUpActivity1 extends AppCompatActivity {

    private static final String TAG = "SignUpActivity1";
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    EditText username, fName, lName, email;
    Button confirm;
    TextView date;

    boolean isDateSelected = false;
    String UDOB = "";

    private static String escapeSpecialCharacters(String input) {
        input = input.replace("\\", "\\\\"); // Replace backslash with double backslash
        input = input.replace("\"", "\\\""); // Replace double quotes with escaped double quotes
        input = input.replace("/", "\\/"); // Replace forward slash with escaped forward slash
        input = input.replace("@", "\\@"); // Replace @ with escaped @
        // Add any additional character replacements if needed

        return input;
    }

    public String CheckName(String Unam){

        Map<String, Object> n = new HashMap<>();

        try {
            n.put("username", username.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Requests UniqName = new Requests(n, "https://lamp.ms.wits.ac.za/home/s2596852/checkUser.php");

        return UniqName.getRequest();
    }

    private static Boolean Flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_up1);
        setContentView(R.layout.activity_sign_in_test);


        //Password validation
        TextInputLayout passwordLayout = findViewById(R.id.newAccountPassword);
        TextInputEditText passwordEditText = findViewById(R.id.newAccPasswordEditText);

        TextInputLayout confirmPasswordLayout = findViewById(R.id.newAccountConfirmPassword);
        TextInputEditText confirmPasswordEditText = findViewById(R.id.newAccConfirmPasswordEditText);

        Button submitButton = findViewById(R.id.newAccConfirmButton);

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
                Log.d(TAG, "onDateSet: yyyy/mm/dd: " +year + "/" +  month + "/" + day);
                UDOB =  year  +"/" + month+"/" + day;
                displayDate.setText(UDOB);
                isDateSelected = true;
            }
        };
        //Return to login page
        ImageButton backButton = (ImageButton) findViewById(R.id.newAccBackButton);
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

        String Uname = username.getText().toString();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameText = fName.getText().toString();
                String lastNameText = lName.getText().toString();
                String emailtext = email.getText().toString();
                String Uname = username.getText().toString();
                String Resp = CheckName(Uname);
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (Resp.equals("Username already exists")) {
                    Toast.makeText(SignUpActivity1.this, "Username taken, please try again", Toast.LENGTH_SHORT).show();
                    username.setError("Choose a different Username");
                    Flag = false;
                } else if (Resp.equals("Username does not exist")) {
                    Flag = true;
                }


                if (username.length() == 0) {
                    username.setError("Enter username");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (Uname.length() < 4) {// Check if the username is at least 4 characters long
                    username.setError("Username must be at least 4 characters long");
                    Toast.makeText(SignUpActivity1.this, "Username must be at least 4 characters long", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (!Uname.matches("[a-zA-Z0-9_]+")) { // Check if the username contains only alphanumeric characters and underscores
                    username.setError("Username can only contain letters, numbers, and underscores");
                    Toast.makeText(SignUpActivity1.this, "Username can only contain letters, numbers, and underscores", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (fName.length() == 0) {
                    fName.setError("Enter first name");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (lName.length() == 0) {
                    lName.setError("Enter last name");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (!firstNameText.matches("[a-zA-Z]+")) {
                    fName.setError("Enter a valid first name");
                    Toast.makeText(SignUpActivity1.this, "Please enter a valid first name", Toast.LENGTH_SHORT).show();
                    Flag = false;
                    // Check if the first name contains only letters
                } else if (!lastNameText.matches("[a-zA-Z]+")) {
                    lName.setError("Enter a valid last name");
                    Toast.makeText(SignUpActivity1.this, "Please enter a valid last name", Toast.LENGTH_SHORT).show();
                    Flag = false;
                    // Check if the last name contains only letters
                } else if (email.length() == 0) {
                    email.setError("Enter email");
                    Toast.makeText(SignUpActivity1.this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()) { //email is valid format
                    email.setError("Enter email");
                    Toast.makeText(SignUpActivity1.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    Flag = false;
                } else if (!isDateSelected) {
                    // Date is not selected, show error message
                    displayDate.setError("Please select a date");
                    Flag = false;
                } else {
                    // Age check
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int currentMonth = calendar.get(Calendar.MONTH) + 1;
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                    String[] selectedDateParts = displayDate.getText().toString().split("/");
                    int selectedYear = Integer.parseInt(selectedDateParts[0]);
                    int selectedDay = Integer.parseInt(selectedDateParts[1]);
                    int selectedMonth = Integer.parseInt(selectedDateParts[2]);

                    int age = currentYear - selectedYear;

                    if (currentMonth < selectedMonth || (currentMonth == selectedMonth && currentDay < selectedDay)) {
                        age--; //Adjusts age if the current date is before the selected birthdate
                        Flag = false;
                    }

                    if (age < 13) {
                        displayDate.setError("You must be 13 years or older");
                        Flag = false;
                    }
                    //add more password validations?

                    if (!password.equals(confirmPassword)) {
                        confirmPasswordLayout.setError("Passwords do not match");
                        Flag = false;
                    } else if (password.length() < 8) {
                        passwordLayout.setError("Password must be greater than 8 characters");
                        Flag = false;
                    } else if (Flag == true) {
                        Toast.makeText(SignUpActivity1.this, "Works :D", Toast.LENGTH_SHORT).show();
                        username.setError(null);
                        fName.setError(null);
                        lName.setError(null);
                        email.setError(null);
                        displayDate.setError(null);
                        confirmPasswordLayout.setError(null);
                        passwordLayout.setError(null);
                        Flag = true;


                        Map<String, Object> map = new HashMap<>();

                        try {
                            map.put("username", username.getText().toString());
                            map.put("fname", fName.getText().toString());
                            map.put("lname", lName.getText().toString());
                            map.put("password", password);
                            map.put("email",email.getText().toString());
                            map.put("DOB", UDOB);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Requests insert = new Requests(map, "https://lamp.ms.wits.ac.za/home/s2596852/insertUser.php");

                        String  msg = insert.postRequest();
                        Toast.makeText(SignUpActivity1.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
    });

}}


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