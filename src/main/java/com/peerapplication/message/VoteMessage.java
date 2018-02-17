package com.peerapplication.message;

import com.peerapplication.model.Vote;

public class VoteMessage extends Message {

    private Vote vote;

    public VoteMessage(){
        super("VoteMessage");
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
