package com.peerapplication.messenger;

public class Peer {

    private String peerAddress;
    private int peerPort;
    private int userID;
    private long lastSeen;

    public Peer(){

    }

    public Peer(int userID, String peerAddress, int peerPort){
        this.peerAddress = peerAddress;
        this.peerPort = peerPort;
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public int getPeerPort() {
        return peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }
}

