package com.example.woofer;

public class UserWoofItem {
    private String userId;
    private String name;
    private String timestamp;
    private String howl;

    public UserWoofItem(String name, String timestamp, String howl) {
        this.name = name;
        this.timestamp = timestamp;
        this.howl = howl;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHowl() {
        return howl;
    }
}
