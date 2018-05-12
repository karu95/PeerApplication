package com.peerapplication.repository;

import com.peerapplication.model.DeletedThread;
import com.peerapplication.model.Tag;
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
        String getQuery = "SELECT * FROM thread WHERE thread_id=? AND thread_id NOT IN (SELECT thread_id FROM deleted)";
        try {
            PreparedStatement getPsmt = connection.prepareStatement(getQuery);
            getPsmt.setString(1, threadID);
            readWriteLock.readLock().lock();
            ResultSet rs = getPsmt.executeQuery();
            while (rs.next()) {
                thread.setThreadID(rs.getString("thread_id"));
                thread.setTitle(rs.getString("title"));
                thread.setUserID(rs.getInt("posted_user"));
                thread.setDescription(rs.getString("description"));
                thread.setTimestamp(rs.getLong("posted_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
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
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate Thread");
            } else {
                e.printStackTrace();
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ArrayList<Thread> getThreads(int userID) {
        ArrayList<Thread> threads = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String getThreadsQuery = "SELECT * FROM thread WHERE posted_user=? AND thread_id NOT IN (SELECT thread_id FROM deleted) ORDER BY posted_time DESC ";
        try {
            PreparedStatement getThreadsPsmt = connection.prepareStatement(getThreadsQuery);
            getThreadsPsmt.setInt(1, userID);
            readWriteLock.readLock().lock();
            ResultSet rs = getThreadsPsmt.executeQuery();
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
        } finally {
            readWriteLock.readLock().unlock();
        }
        return threads;
    }

    public ArrayList<Thread> getLatestThreads(long timestamp) {
        ArrayList<Thread> threads = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String getThreadsQuery = "SELECT * FROM thread WHERE posted_time >= ? AND thread_id NOT IN (SELECT thread_id FROM deleted) ORDER BY posted_time DESC";
        try {
            PreparedStatement getLatestThreadsPsmt = connection.prepareStatement(getThreadsQuery);
            getLatestThreadsPsmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet rs = getLatestThreadsPsmt.executeQuery();
            while (rs.next()) {
                Thread thread = new Thread();
                thread.setThreadID(rs.getString("thread_id"));
                thread.setTitle(rs.getString("title"));
                thread.setUserID(rs.getInt("posted_user"));
                thread.setTimestamp(rs.getLong("posted_time"));
                thread.setDescription(rs.getString("description"));
                thread.setTags(Tag.getTags(thread.getThreadID()));
                threads.add(thread);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return threads;
    }

    public void deleteThread(DeletedThread thread) {
        Connection connection = dbConnection.getConnection();
        String deleteQuery = "INSERT INTO deleted(thread_id, user_id, delete_time) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, thread.getThreadID());
            preparedStatement.setInt(2, thread.getUserID());
            preparedStatement.setLong(3, thread.getDeletedTime());
            readWriteLock.writeLock().lock();
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate deleted thread");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ArrayList<DeletedThread> getDeletedThreads(long timestamp) {
        Connection connection = dbConnection.getConnection();
        ArrayList<DeletedThread> deletedThreads = new ArrayList<>();
        String getQuery = "SELECT * FROM deleted WHERE delete_time >= ?";
        try {
            PreparedStatement psmt = connection.prepareStatement(getQuery);
            psmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                deletedThreads.add(new DeletedThread(resultSet.getString("thread_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getLong("delete_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return deletedThreads;
    }

    public boolean checkDeleted(DeletedThread deletedThread) {
        Connection connection = dbConnection.getConnection();
        boolean deleted = false;
        String checkQuery = "SELECT * FROM deleted WHERE thread_id = ?";
        try {
            PreparedStatement psmt = connection.prepareStatement(checkQuery);
            psmt.setString(1, deletedThread.getThreadID());
            readWriteLock.readLock().lock();
            ResultSet resultSet = psmt.executeQuery();
            if (resultSet.next()) {
                deleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return deleted;
    }
}
