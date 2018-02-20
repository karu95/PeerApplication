package com.peerapplication.handler;

import com.peerapplication.messenger.PeerHandler;
import com.peerapplication.util.Main;
import message.*;

import java.util.Date;

public class BSHandler {

    public void login(LoginMessage loginMessage){
        loginMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(loginMessage, PeerHandler.getBS());
    }

    public void signup(RegisterMessage registerMessage){
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
    }

    public void logout(LogoutMessage logoutMessage){

    }

    public void changePassword(PasswordChangeMessage pwChangeMessage){

    }

    public void handleRequest(RequestStatusMessage message){
        Main.setSystemUserID(message.getUserID());
        if(message.getTitle().equals("LoginStatus")){
            Main.getLoginUpdater().updateUI(message);
        } else if (message.getTitle().equals("RegisterStatus")){
            Main.getRegisterUpdater().updateUI(message);
        }
    }

}
