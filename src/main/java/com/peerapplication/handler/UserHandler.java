package com.peerapplication.handler;

import com.peerapplication.model.User;
import message.UserInfoMessage;
import messenger.PeerHandler;

import java.io.IOException;

public class UserHandler {
    public static void postUser(User user){
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setUser(user);
        PeerHandler.getSenderController().sendToAll(userInfoMessage, PeerHandler.getKnownPeers());
    }

    public static void handleUser(UserInfoMessage userInfoMessage) throws IOException {

        User user = new User();
        user.getUser(userInfoMessage.getUser().getUserID());
        if (user.getUserID()!= userInfoMessage.getUser().getUserID() ){
            PeerHandler.getSenderController().sendToAll(userInfoMessage, PeerHandler.getKnownPeers());
        }

    }
}
