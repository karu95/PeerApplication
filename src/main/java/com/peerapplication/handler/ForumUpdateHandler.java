package com.peerapplication.handler;

import message.ForumUpdateMessage;
import message.Message;
import messenger.Handler;

public class ForumUpdateHandler extends Handler {

    public ForumUpdateHandler() {
    }

    public static void requestUpdate(ForumUpdateMessage forumUpdateMessage) {

    }

    private static void handleUpdate(ForumUpdateMessage forumUpdateMessage) {

    }

    public void handle(Message message) {
        ForumUpdateHandler.handleUpdate((ForumUpdateMessage) message);
    }
}
