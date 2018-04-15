package com.peerapplication.model;

import com.peerapplication.repository.AnswerRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {

    private String answerID;
    private String threadID;
    private String description;
    private ArrayList<Vote> votes;
    private long timestamp;
    private int postedUserID;

    public Answer() {
    }

    public Answer(String answerID) {
        this.answerID = answerID;
    }

    public Answer(String answerID, String threadID, String description, ArrayList<Vote> votes, long timestamp) {
        this.answerID = answerID;
        this.threadID = threadID;
        this.description = description;
        this.votes = votes;
        this.timestamp = timestamp;
    }

    public static ArrayList<Answer> getAnswers(String threadID) {
        AnswerRepository answerRepository = AnswerRepository.getAnswerRepository();
        return answerRepository.getAnswers(threadID);
    }

    public static int getAnswerCount(int userID) {
        AnswerRepository answerRepository = AnswerRepository.getAnswerRepository();
        return answerRepository.getAnswerCount(userID);
    }

    public static ArrayList<Answer> getLatestAnswers(long timestamp) {
        AnswerRepository answerRepository = AnswerRepository.getAnswerRepository();
        return answerRepository.getLatestAnswers(timestamp);
    }

    public static void saveAnswers(ArrayList<Answer> answers) {
        if (!answers.isEmpty()) {
            for (Answer answer : answers) {
                answer.saveAnswer();
            }
        }
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

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void getAnswer(String answerID) {
        AnswerRepository answerRepo = AnswerRepository.getAnswerRepository();
        answerRepo.getAnswer(answerID, this);
        if (this.answerID == answerID) {
            this.setVotes(Vote.getVotes(this.answerID));
        }
    }

    public void saveAnswer() {
        AnswerRepository answerRepo = AnswerRepository.getAnswerRepository();
        answerRepo.saveAnswer(this);
    }
}
