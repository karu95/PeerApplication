package com.peerapplication.validator;

import com.peerapplication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserValidatorTest {

    private static final UserValidator userValidator = UserValidator.getUserValidator();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void getUserValidator() {
        assertEquals(UserValidator.getUserValidator(), userValidator);
    }

    @Test
    void validateWithCorrectData() {
        String name = "mevan karunanayake";
        String email = "abc@def.com";
        user.setName(name);
        user.setEmail(email);
        assertEquals(UserValidator.getUserValidator().validate(user), "Success");
    }

    @Test
    void validateWithFalseName() {
        String name = "mevan200";
        String email = "abc@def.com";
        user.setEmail(email);
        user.setName(name);
        assertEquals(UserValidator.getUserValidator().validate(user), "Invalid name!");
    }

    @Test
    void validateWithFalseEmail() {
        String name = "mevan karunanayake";
        String email = "abc@def";
        user.setName(name);
        user.setEmail(email);
        assertEquals(UserValidator.getUserValidator().validate(user), "Invalid Email Address!");
    }


}