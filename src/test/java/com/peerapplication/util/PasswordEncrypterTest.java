package com.peerapplication.util;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordEncrypterTest {
    @Test
    void SHA1DifferentPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        assertNotEquals(PasswordEncrypter.SHA1("mevan100"), PasswordEncrypter.SHA1("mevan200"));
    }

    @Test
    void SHA1SamePassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        assertEquals(PasswordEncrypter.SHA1("mevan100"), PasswordEncrypter.SHA1("mevan100"));
    }

}