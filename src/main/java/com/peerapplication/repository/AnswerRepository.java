package com.peerapplication.repository;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Vote;
import com.peerapplication.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AnswerRepository {
    private static AnswerRepository answerRepository;

    private DBConnection dbConn;
    private ReadWriteLock readWriteLock;

    private AnswerRepository() {
        dbConn = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public static AnswerRepository getAnswerRepository() {
        if (answerRepository == null) {
            synchronized (AnswerRepository.class) {
                answerRepository = new AnswerRepository();
            }
        }
        return answerRepository;
    }

    public Answer getAnswer(String answerID, Answer answer) {
        Connection connection = dbConn.getConnection();
        String getQuery = "SELECT * FROM answer WHERE answer_id=?";
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setString(1, answerID);
            readWriteLock.readLock().lock();
            ResultSet rs = getStatement.executeQuery();
            while (rs.next()) {
                answer.setAnswerID(rs.getString("answer_id"));
                answer.setDescription(rs.getString("description"));
                answer.setPostedUserID(rs.getInt("posted_user"));
                answer.setThreadID(rs.getString("related_thread"));
                answer.setTimestamp(rs.getLong("posted_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return answer;
    }

    public void saveAnswer(Answer answer) {
        Connection connection = dbConn.getConnection();
        String saveQuery = "INSERT INTO answer(answer_id, description, posted_user, posted_time, related_thread) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement saveStatement = connection.prepareStatement(saveQuery);
            saveStatement.setString(1, answer.getAnswerID());
            saveStatement.setString(2, answer.getDescription());
            saveStatement.setInt(3, answer.getPostedUserID());
            saveStatement.setLong(4, answer.getTimestamp());
            saveStatement.setString(5, answer.getThreadID());
            readWriteLock.writeLock().lock();
            saveStatement.execute();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate Answer");
                return;
            }
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ArrayList<Answer> getAnswers(String threadID) {
        ArrayList<Answer> answers = new ArrayList<>();
        Connection connection = dbConn.getConnection();
        String getAnswersQuery = "SELECT * FROM answer WHERE related_thread=? ORDER BY posted_time ASC";
        try {
            PreparedStatement getStatement = connection.prepareStatement(getAnswersQuery);
            getStatement.setString(1, threadID);
            readWriteLock.readLock().lock();
            ResultSet rs = getStatement.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(rs.getString("answer_id"));
                answer.setDescription(rs.getString("description"));
                answer.setThreadID(rs.getString("related_thread"));
                answer.setTimestamp(rs.getLong("posted_time"));
                answer.setPostedUserID(rs.getInt("posted_user"));
                System.out.println("Getting Votes");
                answer.setVotes(Vote.getVotes(answer.getAnswerID()));
                System.out.println("Votes took");
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return answers;
    }

    public int getAnswerCount(int userID) {
        int count = 0;
        Connection connection = dbConn.getConnection();
        String countQuery = "SELECT COUNT(*) AS cnt FROM answer WHERE posted_user=?";
        try {
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setInt(1, userID);
            readWriteLock.readLock().lock();
            ResultSet rs = countStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return count;
    }

    public ArrayList<Answer> getLatestAnswers(long timestamp) {
        ArrayList<Answer> latestAnswers = new ArrayList<>();
        Connection connection = dbConn.getConnection();
        String latestAnswersQuery = "SELECT * FROM answer WHERE posted_time > ? ORDER BY posted_time ASC";
        try {
            PreparedStatement latestAnsStmt = connection.prepareStatement(latestAnswersQuery);
            latestAnsStmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet rs = latestAnsStmt.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(rs.getString("answer_id"));
                answer.setDescription(rs.getString("description"));
                answer.setThreadID(rs.getString("related_thread"));
                answer.setTimestamp(rs.getLong("posted_time"));
                answer.setPostedUserID(rs.getInt("posted_user"));
                latestAnswers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return latestAnswers;
    }
}
