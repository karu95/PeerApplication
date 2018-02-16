package com.peerapplication.message;

import com.peerapplication.messenger.Peer;

import java.util.ArrayList;

public class RequestStatusMessage extends Message{

    private ArrayList<Peer> activePeers = new ArrayList<Peer>();
    private int accountType;
    private int userID;
    private String status;

    public RequestStatusMessage(){
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Peer> getActivePeers() {
        return activePeers;
    }

    public void setActivePeers(ArrayList<Peer> activePeers) {
        this.activePeers = activePeers;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTitle(String title){
        this.title = title;
    }

}