package com.peerapplication.handler;

import message.Message;
import messenger.Handler;
import messenger.Peer;

public class VoteHandler extends Handler {

    private static VoteHandler voteHandler;

    private VoteHandler() {
    }

    public static VoteHandler getVoteHandler() {
        if (voteHandler == null) {
            synchronized (VoteHandler.class) {
                voteHandler = new VoteHandler();
            }
        }
        return voteHandler;
    }

    @Override
    public void handle(Message message) {

    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
