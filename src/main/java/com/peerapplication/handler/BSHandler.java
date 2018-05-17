package com.peerapplication.handler;

import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.SystemUser;
import com.peerapplication.util.UIUpdateHandler;
import javafx.application.Platform;
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

    public static void login(LoginMessage loginMessage) {                                                               // send login to BS
        PeerHandler.getSenderController().send(loginMessage, PeerHandler.getBS());
        System.out.println("Login Sent!");
    }

    public static void signup(RegisterMessage registerMessage) {                                                        // send signup to BS
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
        System.out.println("Register Sent!");
    }

    public static void logout(LogoutMessage logoutMessage) {                                                            // send logout to BS
        PeerHandler.stopHeartBeat();                                                                                    //stop heartbeat
        SystemUser.setTablesCreated(false);
        logoutMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(logoutMessage, PeerHandler.getBS());
        PeerHandler.getKnownPeers().clear();
        SystemUser.setAccountType(0);
        SystemUser.setSystemUserID(0);                                                                                  //clear systemUser
    }

    public static void changePassword(PasswordChangeMessage pwChangeMessage) {                                          //change password
        PeerHandler.getSenderController().send(pwChangeMessage, PeerHandler.getBS());
        System.out.println("PW Change sent!");
    }

    private static void handleRequest(RequestStatusMessage message) {                                                   //handle request status messages from BS
        if (message.getTitle().equals("LoginStatus")) {                                                                 //login status
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            UIUpdateHandler.informLoginUpdater(message);
        } else if (message.getTitle().equals("RegisterStatus")) {                                                       // register status
            if (message.getStatus().equals("Success")) {
                notifyPeerHandler(message);
            }
            UIUpdateHandler.informRegisterUpdater(message);
        } else if (message.getTitle().equals("PWChangeStatus")) {                                                       //PW change status
            UIUpdateHandler.informUserUpdater(message);
            System.out.println("PW Change status received");
        } else if (message.getTitle().equals("HeartBeatStatus")) {                                                      // heartbeat status
            if (message.getStatus().equals("Not Logged In")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        PeerHandler.stopHeartBeat();
                        PeerHandler.getKnownPeers().clear();
                        SystemUser.setSystemUserID(0);
                        SystemUser.setLastSeen(0);
                        SystemUser.setAccountType(0);
                        ControllerUtility.login();
                    }
                });
            }
        }
    }

    private static void notifyPeerHandler(RequestStatusMessage message) {                                               // notify peer handler method. update successfull login and register
        SystemUser.setSystemUserID(message.getUserID());
        System.out.println(message.getUserID());
        SystemUser.setLastSeen(message.getLastSeen());
        System.out.println("Last seen on " + new Date(message.getLastSeen()));
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
