package com.peerapplication.validator;

import com.peerapplication.model.User;
import org.apache.commons.validator.routines.EmailValidator;

public class UserValidator {

    private static UserValidator userValidator;

    public static UserValidator getUserValidator() {
        if (userValidator == null) {
            synchronized (UserValidator.class) {
                userValidator = new UserValidator();
            }
        }
        return userValidator;
    }

    private UserValidator() {
    }

    public String validate(User user) {
        String error = "Success";
        if (user.getName().matches("[a-zA-Z ]+")) {
            if (!(EmailValidator.getInstance().isValid(user.getEmail()))) {
                error = "Invalid Email Address!";
            }
        } else {
            error = "Invalid name!";
        }
        return error;
    }
}
