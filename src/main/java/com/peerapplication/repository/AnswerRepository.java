package com.peerapplication.repository;

import com.peerapplication.model.Answer;
import com.peerapplication.util.DBConnection;

public class AnswerRepository {
    private DBConnection dbConn;

    public AnswerRepository() {
        dbConn = DBConnection.getDBConnection();
    }

    public Answer getAnswer(String answerID, Answer answer) {

        return answer;
    }

    public void saveAnswer(Answer answer) {

    }
}
