package com.peerapplication.handler;

import com.peerapplication.util.Main;
import com.peerapplication.util.SystemUser;
import message.*;
import messenger.Handler;
import messenger.PeerHandler;

import java.util.Date;

public class BSHandler extends Handler {

    public BSHandler() {
    }

    public static void login(LoginMessage loginMessage) {
        loginMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(loginMessage, PeerHandler.getBS());
    }

    public static void signup(RegisterMessage registerMessage) {
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
    }

    public static void logout(LogoutMessage logoutMessage) {
        logoutMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(logoutMessage, PeerHandler.getBS());
        PeerHandler.stopHeartBeat();
        PeerHandler.getKnownPeers().clear();
        SystemUser.setAccountType(0);
        SystemUser.setSystemUserID(0);
    }

    public static void changePassword(PasswordChangeMessage pwChangeMessage) {

    }

    private static void handleRequest(RequestStatusMessage message) {
        SystemUser.setSystemUserID(message.getUserID());
        System.out.println(message.getUserID());
        SystemUser.setAccountType(message.getAccountType());
        if (message.getTitle().equals("LoginStatus")) {
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            Main.getLoginUpdater().updateUI(message);
        } else if (message.getTitle().equals("RegisterStatus")) {
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            Main.getRegisterUpdater().updateUI(message);
        } else if (message.getTitle().equals("LogoutSuccess")) {
            PeerHandler.stopHeartBeat();
        }
    }

    private static void notifyPeerHandler(RequestStatusMessage message) {
        PeerHandler.setKnownPeers(message.getActivePeers());
        PeerHandler.startHeartBeat();
    }

    public void handle(Message message) {
        BSHandler.handleRequest((RequestStatusMessage) message);
    }

    @Override
    public void handleFailedMesssage(Message message) {

    }
}
