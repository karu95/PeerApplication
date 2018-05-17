package com.peerapplication.handler;

import com.peerapplication.model.*;
import com.peerapplication.model.Thread;
import com.peerapplication.notifcation.NotificationHandler;
import com.peerapplication.util.SystemUser;
import message.ForumUpdateMessage;
import message.Message;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.ArrayList;
import java.util.Date;

public class ForumUpdateHandler extends Handler {

    private static ForumUpdateHandler forumUpdateHandler;
    private static Boolean forumUpdated = false;

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

    public static void requestUpdate() {                                                                                // request forum update from other connected peer
        if (!(PeerHandler.getKnownPeers().isEmpty())) {
            System.out.println("Requesting Forum update");
            PeerHandler.knownPeersReadLock();
            ArrayList<Peer> knownPeers = new ArrayList<>(PeerHandler.getKnownPeers().values());
            PeerHandler.knownPeersReadUnlock();
            Peer peer = knownPeers.get(0);
            ForumUpdateMessage forumUpdateMessage = new ForumUpdateMessage();
            forumUpdateMessage.setStatus("Request");
            forumUpdateMessage.setLastSeen(SystemUser.getLastSeen());
            PeerHandler.getSenderController().send(forumUpdateMessage, peer);
            checkForumUpdate();
        } else {
            forumUpdated();
        }
    }

    private static void handleUpdate(ForumUpdateMessage forumUpdateMessage) {                                           //handle received update
        if (forumUpdateMessage.getStatus().equals("Request")) {                                                         // check if a request
            ForumUpdateMessage processedRequest = new ForumUpdateMessage();                                             // provide updates into the message
            processedRequest.setStatus("Processed Request");
            System.out.println(new Date(forumUpdateMessage.getLastSeen()));
            processedRequest.setRegisteredUsers(User.getLatestUsers(forumUpdateMessage.getLastSeen()));
            processedRequest.setLatestThreads(Thread.getLatestThreads(forumUpdateMessage.getLastSeen()));
            processedRequest.setLatestAnswers(Answer.getLatestAnswers(forumUpdateMessage.getLastSeen()));
            processedRequest.setLatestVotes(Vote.getLatestVotes(forumUpdateMessage.getLastSeen()));
            processedRequest.setDeletedThreads(DeletedThread.getDeletedThreads(forumUpdateMessage.getLastSeen()));
            PeerHandler.getSenderController().send(processedRequest, PeerHandler.getKnownPeers().get(forumUpdateMessage.getSenderID()));
            System.out.println("Request Processed");
        } else if (forumUpdateMessage.getStatus().equals("Processed Request")) {                                        // check if a processed request
            System.out.println("Processed request received");
            User.saveUsers(forumUpdateMessage.getRegisteredUsers());
            Thread.saveThreads(forumUpdateMessage.getLatestThreads());
            Answer.saveAnswers(forumUpdateMessage.getLatestAnswers());
            NotificationHandler.getNotificationHandler().handleAnswers(forumUpdateMessage.getLatestAnswers());
            Vote.saveVotes(forumUpdateMessage.getLatestVotes());
            NotificationHandler.getNotificationHandler().handleVotes(forumUpdateMessage.getLatestVotes());
            DeletedThread.saveDeletedThreads(forumUpdateMessage.getDeletedThreads());
            forumUpdated();
            System.out.println("Forum updated");
        }
    }

    public static void checkForumUpdate() {                                                                             // check for forum update
        if (!forumUpdated) {
            synchronized (forumUpdated) {
                try {
                    System.out.println("Waiting for forum update");
                    forumUpdated.wait();
                    System.out.println("Waiting over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void forumUpdated() {                                                                                //udpated forum
        synchronized (forumUpdated) {
            PeerHandler.startHeartBeat();
            System.out.println("Heart");
            System.out.println("Forum Updated");
            forumUpdated.notifyAll();
        }
    }

    public void handle(Message message) {
        if (message instanceof ForumUpdateMessage) {
            ForumUpdateHandler.handleUpdate((ForumUpdateMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {                                                       // handle forum update failure
        super.handleFailedMessage(message, peer);
        requestUpdate();
    }
}
