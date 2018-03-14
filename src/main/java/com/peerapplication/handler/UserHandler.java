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

public class UserHandler extends Handler {

    private static final Object handleLock = new Object();

    public UserHandler() {
    }

    public static void postUser(User user) {
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setUser(user);
        PeerHandler.getSenderController().sendToAll(userInfoMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
    }

    private static void handleUser(UserInfoMessage userInfoMessage) throws IOException {
        synchronized (handleLock) {
            User user = new User();
            user.getUser(userInfoMessage.getUser().getUserID());
            if (user.getUserID() != userInfoMessage.getUser().getUserID()) {
                PeerHandler.knownPeersReadLock();
                HashMap<Integer, Peer> peers = PeerHandler.getKnownPeers();
                ArrayList<Peer> receivers = new ArrayList<>();
                userInfoMessage.getUser().saveUser();
                for (Map.Entry peer : peers.entrySet()) {
                    if (peer.getKey() == Integer.valueOf(userInfoMessage.getUser().getUserID())
                            || peer.getKey() == Integer.valueOf(userInfoMessage.getSenderID())) {
                        continue;
                    } else {
                        receivers.add((Peer) peer.getValue());
                    }
                }
                PeerHandler.knownPeersReadUnlock();
                PeerHandler.getSenderController().sendToAll(userInfoMessage, receivers);
            }
        }
    }

    public void handle(Message userMessage) {
        try {
            UserHandler.handleUser((UserInfoMessage) userMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
