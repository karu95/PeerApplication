package com.peerapplication.validator;

import com.peerapplication.model.User;
import org.apache.commons.validator.routines.EmailValidator;

public class UserValidator {

    public UserValidator(){}

    public String validate(User user){
        String error= "Success";
        if (user.getName().matches("[a-zA-Z0-9 ]+")){
            if (!(EmailValidator.getInstance().isValid(user.getEmail()))){
                error = "Invalid Email Address!";
            }
        } else {
            error = "Invalid name!";
        }
        return error;
    }
}
