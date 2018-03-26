package message;

import com.peerapplication.util.SystemUser;
import messenger.PeerHandler;

import java.io.Serializable;

public abstract class Message implements Cloneable, Serializable {

    protected String title;
    private int senderID;
    private String receiverAddress;
    private int receiverPort;
    private String senderAddress;
    private int senderPort;
    private long timestamp;
    private int receiverID;

    public Message(String title) {
        this.title = title;
        setSenderID(SystemUser.getSystemUserID());
        setSenderAddress(PeerHandler.getUserAddress());
        setSenderPort(PeerHandler.getUserPort());
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public int getReceiverPort() {
        return receiverPort;
    }

    public void setReceiverPort(int receiverPort) {
        this.receiverPort = receiverPort;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
}