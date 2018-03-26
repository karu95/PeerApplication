package com.peerapplication.handler;

import com.peerapplication.model.User;
import com.peerapplication.util.SystemUser;
import message.ForumUpdateMessage;
import message.Message;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.ArrayList;

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

    public static void requestUpdate() {
        if (!(PeerHandler.getKnownPeers().isEmpty())) {
            PeerHandler.knownPeersReadLock();
            ArrayList<Peer> knownPeers = new ArrayList<>(PeerHandler.getKnownPeers().values());
            PeerHandler.knownPeersReadUnlock();
            Peer peer = knownPeers.get(0);
            ForumUpdateMessage forumUpdateMessage = new ForumUpdateMessage();
            forumUpdateMessage.setStatus("Request");
            forumUpdateMessage.setLastSeen(SystemUser.getLastSeen());
            PeerHandler.getSenderController().send(forumUpdateMessage, peer);
        }
    }

    private static void handleUpdate(ForumUpdateMessage forumUpdateMessage) {
        if (forumUpdateMessage.getStatus().equals("Request")) {
            ForumUpdateMessage processedRequest = new ForumUpdateMessage();
            processedRequest.setStatus("Processed Request");
            processedRequest.setRegisteredUsers(User.getLatestUsers(forumUpdateMessage.getLastSeen()));
        } else if (forumUpdateMessage.getStatus().equals("Processed Request")) {

        }
    }

    public void handle(Message message) {
        ForumUpdateHandler.handleUpdate((ForumUpdateMessage) message);
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {
        super.handleFailedMessage(message, peer);
        requestUpdate();
    }

    public static void checkForumUpdate() {
        if (!forumUpdated) {
            try {
                forumUpdated.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void forumUpdated() {
        forumUpdated = true;
        forumUpdated.notifyAll();
    }
}
