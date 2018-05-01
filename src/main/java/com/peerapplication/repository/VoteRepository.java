package com.peerapplication.repository;

import com.peerapplication.model.Vote;
import com.peerapplication.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class VoteRepository {

    private static VoteRepository voteRepository;

    private DBConnection dbConnection;
    private ReadWriteLock readWriteLock;

    private VoteRepository() {
        dbConnection = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public static VoteRepository getVoteRepository() {
        if (voteRepository == null) {
            synchronized (VoteRepository.class) {
                voteRepository = new VoteRepository();
            }
        }
        return voteRepository;
    }

    public Vote getVote(String answerID, int userID, Vote vote) {
        Connection connection = dbConnection.getConnection();
        String voteQuery = "SELECT * FROM voted WHERE answer_id=? AND user_id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(voteQuery);
            pstmt.setString(1, answerID);
            pstmt.setInt(2, userID);
            readWriteLock.readLock().lock();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vote.setAnswerID(rs.getString("answer_id"));
                vote.setUserID(rs.getInt("user_id"));
                vote.setVotedTime(rs.getLong("voted_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return vote;
    }

    public void saveVote(Vote vote) {
        Connection connection = dbConnection.getConnection();
        String saveQuery = "INSERT INTO voted(user_id, answer_id, voted_time) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(saveQuery);
            pstmt.setInt(1, vote.getUserID());
            pstmt.setString(2, vote.getAnswerID());
            pstmt.setLong(3, vote.getVotedTime());
            readWriteLock.writeLock().lock();
            pstmt.execute();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate Vote");
                return;
            }
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ArrayList<Vote> getVotes(String answerID) {
        ArrayList<Vote> votes = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String getVotesQuery = "SELECT * FROM voted WHERE answer_id=?";
        try {
            PreparedStatement getStatement = connection.prepareStatement(getVotesQuery);
            getStatement.setString(1, answerID);
            System.out.println("Here");
            readWriteLock.readLock().lock();
            ResultSet rs = getStatement.executeQuery();
            System.out.println("Vote Query ran");
            while (rs.next()) {
                Vote vote = new Vote();
                vote.getVote(answerID, rs.getInt("user_id"));
                votes.add(vote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return votes;
    }

    public ArrayList<Vote> getLatestVotes(long timestamp) {
        ArrayList<Vote> latestVotes = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String latestVotesQuery = "SELECT * FROM voted WHERE voted_time > ? ORDER BY voted_time ASC";
        try {
            PreparedStatement latestVotesStmt = connection.prepareStatement(latestVotesQuery);
            latestVotesStmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet rs = latestVotesStmt.executeQuery();
            while (rs.next()) {
                Vote vote = new Vote();
                vote.setAnswerID(rs.getString("answer_id"));
                vote.setUserID(rs.getInt("user_id"));
                vote.setVotedTime(rs.getLong("voted_time"));
                latestVotes.add(vote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return latestVotes;
    }
}
