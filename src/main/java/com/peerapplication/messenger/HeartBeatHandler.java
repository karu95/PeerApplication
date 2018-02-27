package com.peerapplication.messenger;

import com.peerapplication.util.Main;
import com.peerapplication.util.SystemUser;
import message.HeartBeatMessage;

import java.util.ArrayList;

public class HeartBeatHandler implements Runnable{

    private boolean loggedIn;

    public HeartBeatHandler(){
        loggedIn = true;
    }

    @Override
    public void run() {
        while (loggedIn) {
            ArrayList<Peer> peers = PeerHandler.getKnownPeers();
            HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
            heartBeatMessage.setSenderID(SystemUser.getSystemUserID());
            PeerHandler.getSenderController().send(heartBeatMessage, PeerHandler.getBS());
            if (!peers.isEmpty()) {
                for (Peer peer : peers) {
                    PeerHandler.getSenderController().send(heartBeatMessage, peer);
                }
            }
            try {
                Thread.sleep(900000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
