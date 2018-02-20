package com.peerapplication.model;


import com.peerapplication.repository.UserRepository;

import java.awt.image.BufferedImage;

public class User {
    private int userID;
    private String name;
    private String email;
    private BufferedImage userImage;
    private String imageURL;
    private long registerTime;

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

    public BufferedImage getUserImage() {
        return userImage;
    }

    public void setUserImage(BufferedImage userImage) {
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
        UserRepository userRepository = new UserRepository();

    }
}
