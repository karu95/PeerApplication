package com.peerapplication.validator;

import com.peerapplication.model.Thread;
import com.peerapplication.util.SystemUser;

public class ThreadValidator {
    public static String validateThread(Thread thread) {
        String error = "valid";
        if (SystemUser.getSystemUserID() == 0) {
            error = "Please login to post!";
        } else if (thread.getTitle().isEmpty()) {
            error = "Please add a title to the thread!";
        } else if (thread.getDescription().isEmpty()) {
            error = "No thread description!";
        } else if (thread.getTags().isEmpty()) {
            error = "Please add at least one tag!";
        }
        return error;
    }
}
