package com.example.woofer;

public class WoofItem {
    private int likesCount;

    private boolean isLiked;
    private String userId;
    private String timestamp;
    private String howl;


    public WoofItem(String userId, String timestamp, String howl) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.howl = howl;
    }
    public WoofItem(String howl) {
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    }

