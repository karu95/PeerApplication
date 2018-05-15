package com.peerapplication.validator;

import com.peerapplication.model.Answer;
import com.peerapplication.util.SystemUser;

public class AnswerValidator {
    public static String validateAnswer(Answer answer) {
        String error = "valid";
        if (SystemUser.getSystemUserID() == 0) {
            error = "Please Login to proceed";
        } else if (answer.getDescription().isEmpty()) {
            error = "Provide an answer";
        }
        return error;
    }
}
