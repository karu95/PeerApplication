package com.peerapplication.handler;

import message.ForumUpdateMessage;
import message.Message;
import messenger.Handler;
import messenger.Peer;

public class ForumUpdateHandler extends Handler {

    private static ForumUpdateHandler forumUpdateHandler;

    private ForumUpdateHandler() {
    }

    public static ForumUpdateHandler getForumUpdateHandler() {
        if (forumUpdateHandler == null) {
            synchronized (ForumUpdateHandler.class) {
                forumUpdateHandler = new ForumUpdateHandler();
            }
        }
        return forumUpdateHandler;
    }

    public static void requestUpdate(ForumUpdateMessage forumUpdateMessage) {

    }

    private static void handleUpdate(ForumUpdateMessage forumUpdateMessage) {

    }

    public void handle(Message message) {
        ForumUpdateHandler.handleUpdate((ForumUpdateMessage) message);
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
