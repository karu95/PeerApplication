package com.peerapplication.handler;

import message.AnswerMessage;
import message.Message;

public class AnswerHandler extends Handler {

    public AnswerHandler(){}

    public void handle(Message answerMessage){
        handleAnswer((AnswerMessage) answerMessage);
    }

    public static void postAnswer(AnswerMessage answerMessage){

    }

    public static void handleAnswer(AnswerMessage answerMessage){

    }
}
