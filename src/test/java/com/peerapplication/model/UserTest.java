package com.peerapplication.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    private static final String email = "abc@def.com";
    private static final String name = "abc";
    private static final int userID = 100334;
    private static final long updateTime = 1000000;
    private static final long regTime = 100000;
    private static final String url = "def";
    private static final BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("abc");
        user.setEmail("abc@def.com");
        user.setRegisterTime(100000);
        user.setLastProfileUpdate(1000000);
        user.setUserID(100334);
        user.setImageURL("def");
        user.setUserImage(bufferedImage);
    }

    @Test
    void testVariables() {
        assertEquals(user.getName(), name);
        assertEquals(user.getUserID(), userID);
        assertEquals(user.getLastProfileUpdate(), updateTime);
        assertEquals(user.getRegisterTime(), regTime);
        assertEquals(user.getImageURL(), url);
    }

    @Test
    public void saveUser() {
        user.saveUser();
    }

    @Test
    public void getUser() {
        User newUser = new User();
        newUser.getUser(100334);
        assertTrue(newUser.equals(user));
    }
}