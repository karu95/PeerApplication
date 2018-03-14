package messenger;

import message.JoinMessage;
import message.Message;
import message.PeerInfoMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KnownPeerHandler extends Handler {

    KnownPeerHandler() {
    }

    private static void handleJoinMessage(JoinMessage joinMessage) {
        if (joinMessage.getDescription().equals("Join")) {
            Peer peer = new Peer(joinMessage.getSenderID(), joinMessage.getSenderAddress(), joinMessage.getSenderPort());
            peer.setLastSeen(joinMessage.getTimestamp());
            PeerHandler.addKnownPeer(peer);
            System.out.println("Joined " + peer.getUserID());
        }
    }

    static void sendJoinMessageToAll() {
        PeerHandler.knownPeersReadLock();
        HashMap<Integer, Peer> peers = PeerHandler.getKnownPeers();
        ArrayList<Peer> receivers = new ArrayList<>();
        for (Map.Entry peer : peers.entrySet()) {
            receivers.add((Peer) peer.getValue());
        }
        PeerHandler.knownPeersReadUnlock();
        PeerHandler.getSenderController().sendToAll(new JoinMessage("Join"), receivers);
        System.out.println("Join Sent to all known peers");
    }

    static void sendJoinMessage(Peer peer) {
        PeerHandler.getSenderController().send(new JoinMessage("Join"), peer);
        System.out.println("Join Message to " + peer.getUserID());
    }

    static void requestPeerInfo() {

    }

    private static void handlePeerInfoRequest(PeerInfoMessage peerInfoMessage) {

    }

    public void handle(Message message) {
        if (message instanceof JoinMessage) {
            KnownPeerHandler.handleJoinMessage((JoinMessage) message);
        } else if (message instanceof PeerInfoMessage) {
            KnownPeerHandler.handlePeerInfoRequest((PeerInfoMessage) message);
        }
    }
}
