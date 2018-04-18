package com.peerapplication.model;


import com.peerapplication.repository.UserRepository;
import com.peerapplication.util.ImagePack;
import com.peerapplication.util.SystemUser;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int userID;
    private String name;
    private String email;
    private ImagePack userImage;
    private String imageURL;
    private long registerTime;
    private long lastProfileUpdate;

    public User() {
    }

    public User(int userID) {
        this.getUser(userID);
    }

    public User(int userID, String name, String email) {
        this.userID = userID;
        this.email = email;
        this.name = name;
    }

    public static ArrayList<User> getLatestUsers(long timestamp) {
        UserRepository userRepository = UserRepository.getUserRepository();
        return userRepository.getLatestUsers(timestamp);
    }

    public static void saveUsers(ArrayList<User> users) {
        if (!users.isEmpty()) {
            for (User user : users) {
                user.saveUser();
            }
        }
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
        if (userImage != null) {
            return userImage.getImage();
        } else {
            return null;
        }
    }

    public void setUserImage(BufferedImage userImage) {
        this.userImage = new ImagePack(userImage);
        System.out.println("User Image set");
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

    public void getUser(int userID) {
        UserRepository userRepo = UserRepository.getUserRepository();
        userRepo.getUser(userID, this);
    }

    public void saveUser() {
        UserRepository userRepository = UserRepository.getUserRepository();
        userRepository.saveUser(this);
        if (userImage != null) {
            try {
                Thumbnails.of(userImage.getImage()).scale(1).outputFormat("jpg").toFile(new File(SystemUser.getImageLocation() + imageURL));
                System.out.println("User saved");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getUserWithImage(int userID) {
        if (userID != this.userID) {
            getUser(userID);
        }
        if ((imageURL != null) && (userImage == null)) {
            try {
                this.userImage = new ImagePack(ImageIO.read(new File(SystemUser.getImageLocation()
                        + imageURL + ".jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = true;
        User user = (User) object;
        if (this.userID != user.getUserID()) {
            equal = false;
            System.out.println(1);
        } else if (!this.name.equals(user.getName())) {
            equal = false;
            System.out.println(2);
        } else if (!this.email.equals(user.getEmail())) {
            equal = false;
            System.out.println(3);
        } else if (!this.imageURL.equals(user.getImageURL())) {
            equal = false;
            System.out.println(4);
        } else if (this.registerTime != user.getRegisterTime()) {
            equal = false;
            System.out.println(5);
        } else if (userImage != null) {
            if (!(userImage.equals(user.userImage))) {
                equal = false;
                System.out.println(6);
            }
        }
        return equal;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public long getLastProfileUpdate() {
        return lastProfileUpdate;
    }

    public void setLastProfileUpdate(long lastProfileUpdate) {
        this.lastProfileUpdate = lastProfileUpdate;
    }
}
