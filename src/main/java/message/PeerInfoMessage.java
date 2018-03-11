package message;

import messenger.Peer;

import java.util.HashMap;

public class PeerInfoMessage extends Message {

    private String status;
    private HashMap<Integer, Peer> knownPeers;

    public PeerInfoMessage() {
        super("PeerInfoMessage");
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<Integer, Peer> getKnownPeers() {
        return knownPeers;
    }

    public void setKnownPeers(HashMap<Integer, Peer> knownPeers) {
        this.knownPeers = knownPeers;
    }
}
