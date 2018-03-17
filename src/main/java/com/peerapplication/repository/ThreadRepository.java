package com.peerapplication.repository;

import com.peerapplication.model.Thread;
import com.peerapplication.util.DBConnection;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadRepository {

    private static ThreadRepository threadRepository;

    private DBConnection dbConnection;
    private ReadWriteLock readWriteLock;

    public static ThreadRepository getThreadRepository() {
        if (threadRepository == null) {
            synchronized (ThreadRepository.class) {
                threadRepository = new ThreadRepository();
            }
        }
        return threadRepository;
    }

    private ThreadRepository() {
        dbConnection = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public void getThread(String threadID, Thread thread) {

    }

    public void saveThread(Thread thread) {

    }

    public ArrayList<Thread> getThreads(int userID){
        ArrayList<Thread> threads = new ArrayList<>();
        return threads;
    }
}
