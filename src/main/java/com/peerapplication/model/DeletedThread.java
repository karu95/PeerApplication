package com.peerapplication.model;

import com.peerapplication.repository.ThreadRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class DeletedThread implements Serializable {
    private String threadID;
    private int userID;
    private long deletedTime;

    public DeletedThread(String threadID, int userID, long deletedTime) {
        this.deletedTime = deletedTime;
        this.threadID = threadID;
        this.userID = userID;
    }

    public static ArrayList<DeletedThread> getDeletedThreads(long timestamp) {
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return threadRepository.getDeletedThreads(timestamp);
    }

    public static void saveDeletedThreads(ArrayList<DeletedThread> deletedThreads) {
        for (DeletedThread deletedThread : deletedThreads) {
            deletedThread.deleteThread();
        }
    }

    public long getDeletedTime() {
        return deletedTime;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getThreadID() {
        return threadID;
    }

    public void deleteThread() {
        if ((threadID != null) && (userID != 0) && (deletedTime != 0)) {
            ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
            threadRepository.deleteThread(this);
        }
    }

    public boolean checkDeleted() {
        ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
        return threadRepository.checkDeleted(this);
    }
}
