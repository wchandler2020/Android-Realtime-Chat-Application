package com.example.mychatapplication.Utils;

public class Comment {
    private String comment, username, profileImageUrl;

    public Comment() {
    }

    public Comment(String comment, String username, String profileImageUrl) {
        this.comment = comment;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
