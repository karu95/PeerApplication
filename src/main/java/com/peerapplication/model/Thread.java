package com.peerapplication.model;

import com.peerapplication.repository.ThreadRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class Thread implements Serializable {
    private String threadID;
    private long timestamp;
    private String title;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Answer> answers;
    private int userID;

    public Thread() {
    }

    public Thread(String title) {
        this.title = title;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void saveThread() {
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
    }

    public void getThread(String threadID){
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
    }

    public static ArrayList<Thread> getThreads(int userID){
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return threadRepository.getThreads(userID);
    }

}
