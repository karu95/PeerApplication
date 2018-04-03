package com.peerapplication.handler;

import com.peerapplication.model.User;
import message.Message;
import message.UserInfoMessage;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserHandler extends Handler {

    private static ReadWriteLock handleLock = new ReentrantReadWriteLock();
    private static UserHandler userHandler;

    private UserHandler() {
    }

    public static UserHandler getUserHandler() {
        if (userHandler == null) {
            synchronized (UserHandler.class) {
                userHandler = new UserHandler();
            }
        }
        return userHandler;
    }

    public static void postUser(User user) {
        System.out.println("Posting user");
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setUser(user);
        PeerHandler.getSenderController().sendToAll(userInfoMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
        System.out.println("User posted!");
    }

    private static void handleUser(UserInfoMessage userInfoMessage) throws IOException {
        handleLock.writeLock().lock();
        User user = new User();
        user.getUser(userInfoMessage.getUser().getUserID());
        System.out.println("user info received");
        System.out.println("Sender " + userInfoMessage.getSenderID());
        if (!user.equals(userInfoMessage.getUser())) {
            PeerHandler.knownPeersReadLock();
            HashMap<Integer, Peer> peers = PeerHandler.getKnownPeers();
            ArrayList<Peer> receivers = new ArrayList<>();
            userInfoMessage.getUser().saveUser();
            System.out.println("User saved!");
            for (Map.Entry peer : peers.entrySet()) {
                if (peer.getKey().equals(Integer.valueOf(userInfoMessage.getUser().getUserID()))
                        || peer.getKey().equals(Integer.valueOf(userInfoMessage.getSenderID()))) {
                    System.out.println("Removed " + ((Peer) peer.getValue()).getUserID());
                    continue;
                } else {
                    receivers.add((Peer) peer.getValue());
                }
            }
            PeerHandler.knownPeersReadUnlock();
            PeerHandler.getSenderController().sendToAll(userInfoMessage, receivers);
            System.out.println("User info sent to known peers");
        }
        handleLock.writeLock().unlock();
    }

    public void handle(Message userMessage) {
        try {
            UserHandler.handleUser((UserInfoMessage) userMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {
        super.handleFailedMessage(message, peer);
        System.out.println("Message Failed");
    }
}
