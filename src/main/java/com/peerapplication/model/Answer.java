package com.peerapplication.model;

import java.util.ArrayList;

public class Answer {

    private String answerID;
    private String threadID;
    private String description;
    private ArrayList<Vote> votes;
    private long timestamp;
    private int userID;

    public Answer(){ }

    public Answer(String answerID){ this.answerID = answerID;}

    public Answer (String answerID, String threadID, String description, ArrayList<Vote> votes, long timestamp){
        this.answerID = answerID;
        this.threadID = threadID;
        this.description = description;
        this.votes = votes;
        this.timestamp = timestamp;
    }


    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void addVote(Vote vote){
        votes.add(vote);
    }
}
