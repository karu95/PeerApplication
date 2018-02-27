package messenger;

import com.peerapplication.util.SystemUser;
import message.Message;

import java.util.ArrayList;

public class SenderController {
    public void send(Message message, Peer peer) {
        message.setSenderID(SystemUser.getSystemUserID());
        message.setSenderAddress(PeerHandler.getUserAddress());
        message.setSenderPort(PeerHandler.getUserPort());
        message.setReceiverAddress(peer.getPeerAddress());
        message.setReceiverPort(peer.getPeerPort());
        Sender sender = new Sender(message, peer);
        Thread senderThread = new Thread(sender);
        senderThread.start();
    }

    public void sendToAll(Message message, ArrayList<Peer> receivers) {
        if (!receivers.isEmpty()) {
            for (Peer receiver : receivers) {
                send(message, receiver);
            }
        }
    }

}