package com.peerapplication.model;

public class User {
    private int userID;
    private String name;
    private String email;

    public User(){}

    public User(int userID, String name, String email){
        this.userID = userID;
        this.email = email;
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
