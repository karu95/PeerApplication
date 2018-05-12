package messenger;

import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.SystemUser;
import message.BSMessage;
import message.Message;

import java.io.IOException;

public abstract class Handler {

    public abstract void handle(Message message);

    public void handleFailedMessage(Message message, Peer peer) {
        if (!(message instanceof BSMessage)) {
            PeerHandler.knownPeersWriteLock();
            PeerHandler.removeKnownPeer(peer.getUserID());
            PeerHandler.knownPeersWriteLock();
        } else if (message instanceof BSMessage) {
            PeerHandler.stopHeartBeat();
            PeerHandler.getKnownPeers().clear();
            SystemUser.setAccountType(0);
            SystemUser.setSystemUserID(0);
            try {
                ControllerUtility.openBSInfoController(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
