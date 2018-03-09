package com.peerapplication.handler;

import com.peerapplication.model.User;
import message.UserInfoMessage;
import messenger.Peer;
import messenger.PeerHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserHandler extends Handler{

    private static Object handleLock = new Object();

    public static void postUser(User user) {
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setUser(user);
        PeerHandler.getSenderController().sendToAll(userInfoMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
    }

    public static void handleUser(UserInfoMessage userInfoMessage) throws IOException {
        synchronized (handleLock) {
            User user = new User();
            user.getUser(userInfoMessage.getUser().getUserID());
            if (user.getUserID() != userInfoMessage.getUser().getUserID()) {
                HashMap<Integer, Peer> peers = (HashMap<Integer, Peer>) PeerHandler.getKnownPeers().clone();
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
                PeerHandler.getSenderController().sendToAll(userInfoMessage, receivers);
            }
        }
    }
}
