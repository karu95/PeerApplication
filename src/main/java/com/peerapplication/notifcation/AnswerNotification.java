package com.peerapplication.notifcation;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;

public class AnswerNotification extends Notification {
    private Answer answer;

    public AnswerNotification(Answer answer, Thread relatedThread) {
        this.answer = answer;
        this.relatedThread = relatedThread;
        this.timestamp = answer.getTimestamp();
        setDescription();
        System.out.println("Notification added " + description);
    }

    @Override
    protected void setDescription() {
        String description = new User(answer.getPostedUserID()).getName() + " answered your thread \"" + relatedThread.getTitle() + "\"";
        this.description = description;
    }
}
