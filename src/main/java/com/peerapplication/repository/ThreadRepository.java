package com.peerapplication.repository;

import com.peerapplication.model.Thread;
import com.peerapplication.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadRepository {

    private static ThreadRepository threadRepository;

    private DBConnection dbConnection;
    private ReadWriteLock readWriteLock;

    private ThreadRepository() {
        dbConnection = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public static ThreadRepository getThreadRepository() {
        if (threadRepository == null) {
            synchronized (ThreadRepository.class) {
                threadRepository = new ThreadRepository();
            }
        }
        return threadRepository;
    }

    public void getThread(String threadID, Thread thread) {
        Connection connection = dbConnection.getConnection();
        String getQuery = "SELECT * FROM thread WHERE thread_id=?;";
        try {
            PreparedStatement getPsmt = connection.prepareStatement(getQuery);
            getPsmt.setString(1, threadID);
            readWriteLock.readLock().lock();
            ResultSet rs = getPsmt.executeQuery();
            readWriteLock.readLock().unlock();
            while (rs.next()) {
                thread.setThreadID(rs.getString("thread_id"));
                thread.setTitle(rs.getString("title"));
                thread.setUserID(rs.getInt("posted_user"));
                thread.setDescription(rs.getString("description"));
                thread.setTimestamp(rs.getLong("posted_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveThread(Thread thread) {
        Connection connection = dbConnection.getConnection();
        String saveQuery = "INSERT INTO thread(thread_id, title, posted_user, description, posted_time) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement saveQueryPsmt = connection.prepareStatement(saveQuery);
            saveQueryPsmt.setString(1, thread.getThreadID());
            saveQueryPsmt.setString(2, thread.getTitle());
            saveQueryPsmt.setInt(3, thread.getUserID());
            saveQueryPsmt.setString(4, thread.getDescription());
            saveQueryPsmt.setLong(5, thread.getTimestamp());
            readWriteLock.writeLock().lock();
            saveQueryPsmt.execute();
            readWriteLock.writeLock().unlock();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate Thread");
                return;
            } else {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Thread> getThreads(int userID) {
        ArrayList<Thread> threads = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String getThreadsQuery = "SELECT * FROM thread WHERE posted_user=? ORDER BY posted_time DESC ";
        try {
            PreparedStatement getThreadsPsmt = connection.prepareStatement(getThreadsQuery);
            getThreadsPsmt.setInt(1, userID);
            readWriteLock.readLock().lock();
            ResultSet rs = getThreadsPsmt.executeQuery();
            readWriteLock.readLock().unlock();
            while (rs.next()) {
                Thread thread = new Thread();
                thread.setThreadID(rs.getString("thread_id"));
                thread.setTitle(rs.getString("title"));
                thread.setUserID(rs.getInt("posted_user"));
                thread.setDescription(rs.getString("description"));
                thread.setTimestamp(rs.getLong("posted_time"));
                threads.add(thread);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threads;
    }

    public ArrayList<Thread> getLatestThreads(long timestamp) {
        ArrayList<Thread> threads = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String getThreadsQuery = "SELECT * FROM thread WHERE posted_time = ? ORDER BY posted_time DESC";
        try {
            PreparedStatement getLatestThreadsPsmt = connection.prepareStatement(getThreadsQuery);
            getLatestThreadsPsmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet rs = getLatestThreadsPsmt.executeQuery();
            readWriteLock.readLock().unlock();
            while (rs.next()) {
                Thread thread = new Thread();
                thread.setThreadID(rs.getString("thread_id"));
                thread.setTitle(rs.getString("title"));
                thread.setUserID(rs.getInt("posted_user"));
                thread.setTimestamp(rs.getLong("posted_time"));
                thread.setDescription(rs.getString("description"));
                threads.add(thread);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threads;
    }
}
