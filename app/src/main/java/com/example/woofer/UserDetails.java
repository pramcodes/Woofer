package com.example.woofer;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class UserDetails implements Parcelable {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;

    public UserDetails() {

    }

    protected UserDetails(Parcel in) {
        userId = in.readInt();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        dateOfBirth = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(dateOfBirth);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}