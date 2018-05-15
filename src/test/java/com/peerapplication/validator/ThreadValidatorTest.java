package com.peerapplication.validator;

import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.util.SystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadValidatorTest {

    private Thread thread;

    @BeforeEach
    void setUp() {
        thread = new Thread();
    }

    @Test
    void validateThreadNotLogin() {
        SystemUser.setSystemUserID(0);
        assertEquals(ThreadValidator.validateThread(thread), "Please login to post!");
    }

    @Test
    void validateThread() {


    }

    @Test
    void validateThreadNoTitle() {
        SystemUser.setSystemUserID(100);
        thread.setTitle("");
        assertEquals(ThreadValidator.validateThread(thread), "Please add a title to the thread!");
    }

    @Test
    void validateThreadNoDescription() {
        SystemUser.setSystemUserID(100);
        thread.setTitle("hiiii");
        thread.setDescription("");
        assertEquals(ThreadValidator.validateThread(thread), "No thread description!");
    }

    @Test
    void validateThreadNoTags() {
        SystemUser.setSystemUserID(100);
        thread.setTitle("hiiii");
        thread.setDescription("asdashdghsad");
        thread.setTags(new ArrayList<Tag>());
        assertEquals(ThreadValidator.validateThread(thread), "Please add at least one tag!");
    }
}