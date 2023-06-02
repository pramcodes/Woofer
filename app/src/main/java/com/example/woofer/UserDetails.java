package com.example.woofer;

import java.util.Date;

public class UserDetails {
    String  Uname, Fname , Lname , password, email;
    Date DOB ;
    int Uid ;

    public  UserDetails(int ID ,String alias,String name ,String surname, String passcode, String mail , Date Birthday  ){
        Uname = alias;
        Fname = name;
        Lname = surname;
        Uid = ID;
        password = passcode;
        email = mail ;
        DOB = Birthday;
    }

    public int getUid(){
        return Uid;
    }
    public String geUFName(){
        return Uname;
    }

    public String getFName(){
        return Fname;
    }

    public String getLName(){
        return Lname;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public Date getDOB(){
        return DOB;
    }


}
