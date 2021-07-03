package com.example.mychatapplication.Utils;

public class Users {
    private String city, country,profession,profileImage, username, status;

    public Users(String city, String country, String profession, String profileImage, String username, String status) {
        this.city = city;
        this.country = country;
        this.profession = profession;
        this.profileImage = profileImage;
        this.username = username;
        this.status = status;
    }

    public Users() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
