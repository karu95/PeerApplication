package com.peerapplication.handler;

import message.AnswerMessage;
import message.Message;
import messenger.Handler;

public class AnswerHandler extends Handler {

    public AnswerHandler() {
    }

    public static void postAnswer(AnswerMessage answerMessage) {

    }

    private static void handleAnswer(AnswerMessage answerMessage) {

    }

    public void handle(Message answerMessage) {
        handleAnswer((AnswerMessage) answerMessage);
    }

    @Override
    public void handleFailedMesssage(Message message) {

    }
}
