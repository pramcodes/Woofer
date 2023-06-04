package com.example.woofer;

import java.util.Date;

public class UserDetails {
    private static int userId;
    private static String username;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String dateOfBirth;

    public UserDetails() {

    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void getUserId(int id) {
         userId = id;
    }

    public static void setUsername(String uname) {
        username = uname;
    }

    public static void  setFirstName(String Fname) {
        firstName = Fname;
    }

    public static void getLastName(String LName) {
        lastName = LName;
    }

    public static void getEmail(String mail) {
        email = mail;
    }

    public static void  getDateOfBirth(String DOB) {
        dateOfBirth = DOB;
    }
}
