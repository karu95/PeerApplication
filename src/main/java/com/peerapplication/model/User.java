package com.peerapplication.model;


import com.peerapplication.repository.UserRepository;
import javafx.scene.image.Image;

public class User {
    private int userID;
    private String name;
    private String email;
    private Image userImage;
    private String imageURL;
    private long registerTime;

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public void getUser(int userID){
        UserRepository userRepo = new UserRepository();
        userRepo.getUser(userID, this);
    }

    public void saveUser(){

    }
}
