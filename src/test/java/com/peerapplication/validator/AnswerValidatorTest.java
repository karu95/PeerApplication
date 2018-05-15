package com.peerapplication.validator;

import com.peerapplication.model.Answer;
import com.peerapplication.util.SystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerValidatorTest {

    private Answer answer;

    @BeforeEach
    void setUp() {
        answer = new Answer();
    }

    @Test
    void validateAnswerCorrect() {
        SystemUser.setSystemUserID(100);
        answer.setDescription("hasjkdhsakjdhjsakdhjsakd");
        assertEquals(AnswerValidator.validateAnswer(answer), "valid");
    }

    @Test
    void validateAnswerEmpty() {
        SystemUser.setSystemUserID(110);
        answer.setDescription("");
        assertEquals(AnswerValidator.validateAnswer(answer), "Provide an answer");
    }

    @Test
    void validateAnswerNotLogin() {
        SystemUser.setSystemUserID(0);
        assertEquals(AnswerValidator.validateAnswer(answer), "Please Login to proceed");

    }

}