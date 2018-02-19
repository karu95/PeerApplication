package com.peerapplication.handler;

import com.peerapplication.messenger.PeerHandler;
import com.peerapplication.util.Main;
import message.*;

import java.util.Date;

public class BSHandler {

    public void login(LoginMessage loginMessage){

    }

    public void signup(RegisterMessage registerMessage){
        registerMessage.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        PeerHandler.getSenderController().send(registerMessage, PeerHandler.getBS());
        System.out.println("Sent to Controller");
    }

    public void logout(LogoutMessage logoutMessage){

    }

    public void changePassword(PasswordChangeMessage pwChangeMessage){

    }

    public void handleRequest(RequestStatusMessage message){
        if(message.getTitle().equals("LoginStatus")){
            Main.getLoginListener().updateUI(message);
        } else if (message.getTitle().equals("RegisterStatus")){
            Main.getRegisterListener().updateUI(message);
        }

    }

}
