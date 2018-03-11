package com.peerapplication.repository;

import com.peerapplication.model.Vote;
import com.peerapplication.util.DBConnection;

public class VoteRepository {

    private DBConnection dbConnection;

    public VoteRepository() {
        dbConnection = DBConnection.getDBConnection();
    }

    public Vote getVote(String voteID, Vote vote) {
        return vote;
    }

    public void saveVote(Vote vote) {

    }
}
