package messenger;

import message.BSMessage;
import message.Message;

public abstract class Handler {

    public abstract void handle(Message message);

    public void handleFailedMessage(Message message, Peer peer) {
        System.out.println("here");
        if (!(message instanceof BSMessage)) {
            PeerHandler.knownPeersWriteLock();
            PeerHandler.removeKnownPeer(peer.getUserID());
            PeerHandler.knownPeersWriteLock();
        }
    }
}
