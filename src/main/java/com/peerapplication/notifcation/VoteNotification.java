package com.peerapplication.notifcation;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.model.Vote;

public class VoteNotification extends Notification {
    private Vote vote;

    public VoteNotification(Vote vote) {
        this.vote = vote;
        this.relatedThread = new Thread(new Answer(vote.getAnswerID()).getThreadID());
        this.timestamp = vote.getVotedTime();
        setDescription();
        System.out.println("Vote Notification " + description);
    }

    @Override
    protected void setDescription() {
        String description = new User(vote.getUserID()).getName() + " voted your answer for thread \"" + relatedThread.getTitle() + "\"";
        this.description = description;
    }
}
