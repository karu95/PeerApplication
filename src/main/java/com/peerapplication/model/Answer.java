package com.peerapplication.model;

import com.peerapplication.repository.AnswerRepository;

import java.util.ArrayList;

public class Answer {

    private String answerID;
    private String threadID;
    private String description;
    private ArrayList<Vote> votes;
    private long timestamp;
    private int postedUserID;

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

    public int getPostedUserID() {
        return postedUserID;
    }

    public void setPostedUserID(int postedUserID) {
        this.postedUserID = postedUserID;
    }

    public void addVote(Vote vote){
        votes.add(vote);
    }

    public void getAnswer(String answerID){
        AnswerRepository answerRepo = new AnswerRepository();
        answerRepo.getAnswer(answerID, this);
    }
    public void saveAnswer(){
        AnswerRepository answerRepo = new AnswerRepository();

    }
}
