package com.peerapplication.model;


import javafx.scene.image.Image;

public class User {
    private int userID;
    private String name;
    private String email;
    private Image userImage;

    public User(){}

    public User(int userID, String name, String email, Image userImage){
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.userImage = userImage;
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

    public Image getUserImage() {
        return userImage;
    }

    public void setUserImage(Image userImage) {
        this.userImage = userImage;
    }
}
