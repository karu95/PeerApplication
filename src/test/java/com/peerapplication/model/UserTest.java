package com.peerapplication.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private static final String email = "abc@def.com";
    private static final String name = "abc";
    private static final int userID = 5;
    private static final long updateTime = 1000000;
    private static final long regTime = 100000;
    private static final String url = "def";
    private static final BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("abc");
        user.setEmail("abc@def.com");
        user.setRegisterTime(100000);
        user.setLastProfileUpdate(1000000);
        user.setUserID(5);
        user.setImageURL("def");
        user.setUserImage(bufferedImage);
    }

    @Test
    void testVariables(User user) {
        assertEquals(user.getName(), name);
        assertEquals(user.getUserID(), userID);
        assertEquals(user.getLastProfileUpdate(), updateTime);
        assertEquals(user.getRegisterTime(), regTime);
        assertEquals(user.getImageURL(), url);
        assertEquals(user.getUserImage(), bufferedImage);
    }

    @Test
    public void saveUser() {
        user.saveUser();
    }

    @Test
    public void getUser() {

    }
}