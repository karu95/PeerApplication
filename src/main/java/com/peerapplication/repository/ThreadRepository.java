package com.peerapplication.repository;

import com.peerapplication.util.DBConnection;

public class ThreadRepository {
    private DBConnection dbConnection;

    public ThreadRepository() {
        dbConnection = new DBConnection();
    }

    public Thread getThread(String threadID, Thread thread) {
        return thread;
    }

    public void saveThread(Thread thread) {

    }
}
