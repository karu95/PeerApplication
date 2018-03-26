package com.peerapplication.model;

import com.peerapplication.repository.ThreadRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class Thread implements Serializable {
    private String threadID;
    private long timestamp;
    private String title;
    private String description;
    private ArrayList<Tag> tags;
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
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

    public void addTag(Tag tag) {
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
        threadRepository.saveThread(this);
        Tag.saveTags(tags, threadID);
    }

    public void getThread(String threadID){
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        threadRepository.getThread(threadID, this);
        if (threadID.equals(this.threadID)) {
            tags = Tag.getTags(threadID);
            answers = Answer.getAnswers(threadID);
        }
    }

    public static ArrayList<Thread> getUserThreads(int userID) {
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return threadRepository.getThreads(userID);
    }

    public static ArrayList<Thread> getLatestThreads() {
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return threadRepository.getLatestThreads();
    }

    /*
    public static ArrayList<Thread> getLatestThreads(long timestamp){
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return
    }
    */
}
