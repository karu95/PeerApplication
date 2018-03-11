package com.peerapplication.repository;

import com.peerapplication.util.DBConnection;

public class ThreadRepository {
    private DBConnection dbConnection;

    public ThreadRepository() {
        dbConnection = DBConnection.getDBConnection();
    }

    public Thread getThread(String threadID, Thread thread) {
        return thread;
    }

    public void saveThread(Thread thread) {

    }
}
