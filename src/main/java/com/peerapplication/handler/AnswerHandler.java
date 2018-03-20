package com.peerapplication.handler;

import message.AnswerMessage;
import message.Message;
import messenger.Handler;
import messenger.Peer;

public class AnswerHandler extends Handler {

    private static AnswerHandler answerHandler;

    private AnswerHandler() {

    }

    public static AnswerHandler getAnswerHandler() {
        if (answerHandler == null) {
            synchronized (AnswerHandler.class) {
                answerHandler = new AnswerHandler();
            }
        }
        return answerHandler;
    }

    public static void postAnswer(AnswerMessage answerMessage) {

    }

    private static void handleAnswer(AnswerMessage answerMessage) {

    }

    public void handle(Message answerMessage) {
        handleAnswer((AnswerMessage) answerMessage);
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
