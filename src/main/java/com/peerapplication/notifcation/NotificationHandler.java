package com.peerapplication.notifcation;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.model.Vote;
import com.peerapplication.util.SystemUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class NotificationHandler {
    private static NotificationHandler notificationHandler;
    private ObservableList<Notification> notifications;

    private NotificationHandler() {
        notifications = FXCollections.observableArrayList();
    }

    public static NotificationHandler getNotificationHandler() {
        if (notificationHandler == null) {
            synchronized (NotificationHandler.class) {
                notificationHandler = new NotificationHandler();
            }
        }
        return notificationHandler;
    }

    public void handleAnswer(Answer answer) {
        Thread relatedThread = new Thread(answer.getThreadID());
        if (relatedThread.getUserID() == SystemUser.getSystemUserID()) {
            insertNotification(new AnswerNotification(answer, relatedThread));
        }
    }

    public void handleVote(Vote vote) {
        Answer relatedAnswer = new Answer(vote.getAnswerID());
        if (relatedAnswer.getPostedUserID() == SystemUser.getSystemUserID()) {
            insertNotification(new VoteNotification(vote));
        }
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    public void handleVotes(ArrayList<Vote> votes) {
        for (Vote vote : votes) {
            handleVote(vote);
        }
    }

    public void handleAnswers(ArrayList<Answer> answers) {
        for (Answer answer : answers) {
            handleAnswer(answer);
        }
    }

    public void notificationRead(Notification notification) {
        synchronized (notifications) {
            notifications.remove(notification);
        }
    }

    private void insertNotification(Notification notification) {
        synchronized (notifications) {
            if (notifications.isEmpty()) {
                notifications.add(notification);
            } else {
                for (int i = 0; i < notifications.size(); i++) {
                    if (notifications.get(i).timestamp <= notification.timestamp) {
                        notifications.add(i, notification);
                        break;
                    }
                }
            }
        }
    }
}
