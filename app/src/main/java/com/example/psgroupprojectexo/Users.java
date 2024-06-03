package com.example.psgroupprojectexo;

public class Users {

    String userId, name, profile;

    public Users(String userId, String name, String profile) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
    }

    public Users() {

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
}
