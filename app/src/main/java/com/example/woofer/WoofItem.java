package com.example.woofer;

public class WoofItem {
    private String userId;
    private String timestamp;
    private String howl;

    public WoofItem(String userId, String timestamp, String howl) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.howl = howl;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHowl() {
        return howl;
    }
}
