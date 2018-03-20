package com.peerapplication.handler;

import message.Message;
import messenger.Handler;
import messenger.Peer;

public class ThreadHandler extends Handler {

    private static ThreadHandler threadHandler;

    private ThreadHandler() {
    }

    public static ThreadHandler getThreadHandler() {
        if (threadHandler == null) {
            synchronized (ThreadHandler.class) {
                threadHandler = new ThreadHandler();
            }
        }
        return threadHandler;
    }

    @Override
    public void handle(Message message) {
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
