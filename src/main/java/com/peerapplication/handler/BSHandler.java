package com.peerapplication.handler;

import com.peerapplication.util.Main;
import com.peerapplication.util.SystemUser;
import message.*;
import messenger.PeerHandler;

import java.util.Date;

public class BSHandler {

    public static void login(LoginMessage loginMessage){
        loginMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(loginMessage, PeerHandler.getBS());
    }

    public static void signup(RegisterMessage registerMessage){
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
    }

    public static void logout(LogoutMessage logoutMessage){
        logoutMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(logoutMessage, PeerHandler.getBS());
        PeerHandler.stopHeartBeat();
        PeerHandler.getKnownPeers().clear();
        SystemUser.setAccountType(0);
        SystemUser.setSystemUserID(0);
    }

    public static void changePassword(PasswordChangeMessage pwChangeMessage){
        
    }

    public static void handleRequest(RequestStatusMessage message){
        SystemUser.setSystemUserID(message.getUserID());
        System.out.println(message.getUserID());
        SystemUser.setAccountType(message.getAccountType());
        if (message.getStatus().equals("Success")){
            PeerHandler.startHeartBeat();
            PeerHandler.setKnownPeers(message.getActivePeers());
        }
        if(message.getTitle().equals("LoginStatus")){
            Main.getLoginUpdater().updateUI(message);
        } else if (message.getTitle().equals("RegisterStatus")){
            Main.getRegisterUpdater().updateUI(message);
        }
    }
}
