package com.peerapplication.messenger;

import message.HeartBeatMessage;

import java.util.ArrayList;

public class HeartBeatHandler implements Runnable{

    @Override
    public void run() {
        while (true){
            ArrayList<Peer> peers = PeerHandler.getKnownPeers();
            PeerHandler.getSenderController().send(new HeartBeatMessage(), PeerHandler.getBS());
            if (!peers.isEmpty()){
                for (Peer peer: peers){
                        PeerHandler.getSenderController().send(new HeartBeatMessage(), peer);
                }
            }
            try {
                Thread.sleep(900000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
