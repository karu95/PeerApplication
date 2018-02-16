package com.peerapplication.messenger;

import com.peerapplication.message.Message;
import com.peerapplication.util.Main;

import java.util.ArrayList;

public class SenderController {
    public void send(Message message) {

    }

    public void sendAll(Message message) {
        int senderID = message.getSenderID();
        ArrayList<Peer> peers = PeerHandler.getKnownPeers();
        synchronized(peers){
            for (Peer peer:peers){
                if (peer.getUserID()!=senderID){
                    message.setSenderAddress(PeerHandler.getUserAddress());
                    message.setSenderPort(PeerHandler.getUserPort());
                    message.setReceiverAddress(peer.getPeerAddress());
                    message.setReceiverPort(peer.getPeerPort());
                    message.setSenderID(Main.getSystemUserID());
                }
            }
        }
    }
}
