package com.peerapplication.handler;

import com.peerapplication.util.Main;
import com.peerapplication.util.SystemUser;
import message.*;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.Date;

public class BSHandler extends Handler {

    private static BSHandler bsHandler;

    private BSHandler() {
    }

    public static BSHandler getBSHandler() {
        if (bsHandler == null) {
            synchronized (BSHandler.class) {
                bsHandler = new BSHandler();
            }
        }
        return bsHandler;
    }

    public static void login(LoginMessage loginMessage) {
        PeerHandler.getSenderController().send(loginMessage, PeerHandler.getBS());
        System.out.println("Login Sent!");
    }

    public static void signup(RegisterMessage registerMessage) {
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
        System.out.println("Register Sent!");
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
        }
    }

    private static void notifyPeerHandler(RequestStatusMessage message) {
        SystemUser.setSystemUserID(message.getUserID());
        System.out.println(message.getUserID());
        SystemUser.setLastSeen(message.getLastSeen());
        System.out.println(new Date(message.getLastSeen()));
        SystemUser.setAccountType(message.getAccountType());
        System.out.println("Active size " + message.getActivePeers().size());
        PeerHandler.setKnownPeers(message.getActivePeers());
        PeerHandler.startHeartBeat();
    }

    public void handle(Message message) {
        BSHandler.handleRequest((RequestStatusMessage) message);
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {
        super.handleFailedMessage(message, peer);
        System.out.println("Failed Message : " + message.getTitle());
        System.out.println("No Network Connection");
    }
}
