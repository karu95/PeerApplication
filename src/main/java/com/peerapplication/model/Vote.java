package com.peerapplication.model;

import com.peerapplication.repository.VoteRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class Vote implements Serializable {
    private int userID;
    private String answerID;
    private long votedTime;

    public Vote(int userID, String answerID) {
        this.answerID = answerID;
        this.userID = userID;
    }

    public Vote() {
    }

    public static ArrayList<Vote> getVotes(String answerID) {
        VoteRepository voteRepository = VoteRepository.getVoteRepository();
        return voteRepository.getVotes(answerID);
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void saveVote() {
        VoteRepository voteRepository = VoteRepository.getVoteRepository();
        voteRepository.saveVote(this);
    }

    public void getVote(String answerID, int userID) {
        VoteRepository voteRepository = VoteRepository.getVoteRepository();
        voteRepository.getVote(answerID, userID, this);
    }

    public long getVotedTime() {
        return votedTime;
    }

    public void setVotedTime(long votedTime) {
        this.votedTime = votedTime;
    }

    public static ArrayList<Vote> getLatestVotes(long timestamp) {
        VoteRepository voteRepository = VoteRepository.getVoteRepository();
        return voteRepository.getLatestVotes(timestamp);
    }

    public static void saveVotes(ArrayList<Vote> votes) {
        if (!votes.isEmpty()) {
            for (Vote vote : votes) {
                vote.saveVote();
            }
        }
    }
}
