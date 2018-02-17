package com.peerapplication.model;

public class Vote {
    private int userID;
    private String answerID;

    public Vote(int userID, String answerID){
        this.answerID = answerID;
        this.userID = userID;
    }

    public Vote(){}

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
