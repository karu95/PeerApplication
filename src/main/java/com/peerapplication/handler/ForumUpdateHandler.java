package com.peerapplication.handler;

import message.ForumUpdateMessage;
import message.Message;

public class ForumUpdateHandler extends Handler {

    public ForumUpdateHandler() {
    }

    public void handle(Message message) {
        ForumUpdateHandler.handleUpdate((ForumUpdateMessage) message);
    }

    public static void requestUpdate(ForumUpdateMessage forumUpdateMessage) {

    }

    public static void handleUpdate(ForumUpdateMessage forumUpdateMessage) {

    }
}
