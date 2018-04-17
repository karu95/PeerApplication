package com.peerapplication.handler;

import com.peerapplication.util.SystemUser;
import com.peerapplication.util.UIUpdateHandler;
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
        PeerHandler.getSenderController().send(pwChangeMessage, PeerHandler.getBS());
        System.out.println("PW Change sent!");
    }

    private static void handleRequest(RequestStatusMessage message) {
        if (message.getTitle().equals("LoginStatus")) {
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            UIUpdateHandler.informLoginUpdater(message);
        } else if (message.getTitle().equals("RegisterStatus")) {
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            UIUpdateHandler.informRegisterUpdater(message);
        } else if (message.getTitle().equals("PWChangeStatus")) {
            UIUpdateHandler.informUserUpdater(message);
            System.out.println("PW Change status received");
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
    }

    public void handle(Message message) {
        if (message instanceof BSMessage) {
            BSHandler.handleRequest((RequestStatusMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {
        super.handleFailedMessage(message, peer);
        System.out.println("Unable to connect BS");
    }
}
