package com.peerapplication.repository;

import com.peerapplication.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {

    private static final UserRepository userRepository = UserRepository.getUserRepository();
    private UserRepository otherUserRepository;
    private User user;

    @BeforeAll
    static void init() {
        new TableRepository().createTables();
    }

    @BeforeEach
    void setUp() {
        otherUserRepository = UserRepository.getUserRepository();
        user = new User();
        user.setName("abc");
        user.setEmail("abc@def.com");
        user.setRegisterTime(100000);
        user.setLastProfileUpdate(1000000);
        user.setUserID(100333);
    }

    @Test
    void getUserRepository() {
        assertEquals(otherUserRepository, userRepository);
    }

    @Test
    void getUser() {
        User newUser = new User();
        userRepository.getUser(100333, newUser);
        assertEquals(newUser.getUserID(), 100333);
    }

    @Test
    void saveUser() {
        otherUserRepository.saveUser(user);
    }

    @Test
    void getLatestUsers() {
        ArrayList<User> users = userRepository.getLatestUsers(0);
        assertEquals(users.size(), 1);
    }

}