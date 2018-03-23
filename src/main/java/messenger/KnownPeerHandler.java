package messenger;

import message.JoinMessage;
import message.Message;
import message.PeerInfoMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KnownPeerHandler extends Handler {

    private static KnownPeerHandler knownPeerHandler;

    private KnownPeerHandler() {
    }

    static KnownPeerHandler getKnownPeerHandler() {
        if (knownPeerHandler == null) {
            synchronized (KnownPeerHandler.class) {
                knownPeerHandler = new KnownPeerHandler();
            }
        }
        return knownPeerHandler;
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
        ArrayList<Peer> receivers = new ArrayList<>(PeerHandler.getKnownPeers().values());
        for (Peer p : receivers) {
            System.out.println("Port " + p.getPeerPort());
        }
        PeerHandler.getSenderController().sendToAll(new JoinMessage("Join"), receivers);
        PeerHandler.knownPeersReadUnlock();
        System.out.println("Join Sent to all known peers");
    }

    static void sendJoinMessage(Peer peer) {
        PeerHandler.getSenderController().send(new JoinMessage("Join"), peer);
        System.out.println("Join Message to " + peer.getUserID());
    }

    static void requestPeerInfo() {
        System.out.println("Here Rquesting");
        ArrayList<Peer> peers = new ArrayList<>(PeerHandler.getKnownPeers().values());
        if (!peers.isEmpty()) {
            Peer peer = peers.get(0);
            System.out.println("PeerPopped " + PeerHandler.getKnownPeers().size());
            PeerInfoMessage peerInfoMessage = new PeerInfoMessage();
            peerInfoMessage.setStatus("Request");
            PeerHandler.getSenderController().send(peerInfoMessage, peer);
            System.out.println("Request Sent");
        }
    }

    private static void handlePeerInfoRequest(PeerInfoMessage peerInfoMessage) {
        System.out.println("Handling Peer Request");
        System.out.println(peerInfoMessage.getStatus());
        if (peerInfoMessage.getStatus().equals("Request")) {
            HashMap<Integer, Peer> knownPeers = (HashMap<Integer, Peer>) PeerHandler.getKnownPeers().clone();
            Peer peer = knownPeers.get(peerInfoMessage.getSenderID());
            knownPeers.remove(peerInfoMessage.getSenderID());
            PeerInfoMessage peerDetailMessage = new PeerInfoMessage();
            peerDetailMessage.setStatus("ProcessedRequest");
            PeerHandler.getSenderController().send(peerDetailMessage, peer);
        } else if (peerInfoMessage.getStatus().equals("ProcessedRequest")) {
            HashMap<Integer, Peer> knownPeers = peerInfoMessage.getKnownPeers();
            if (!knownPeers.isEmpty()) {
                for (Map.Entry peer : knownPeers.entrySet()) {
                    if (PeerHandler.getKnownPeers().containsKey(peer.getKey())) {
                        continue;
                    } else {
                        PeerHandler.addKnownPeer((Peer) peer.getValue());
                        sendJoinMessage((Peer) peer.getValue());
                    }
                }
            }
            System.out.println("@KnownPeerHandler - peerinfo message processed");
        }
    }

    public void handle(Message message) {
        if (message instanceof JoinMessage) {
            KnownPeerHandler.handleJoinMessage((JoinMessage) message);
        } else if (message instanceof PeerInfoMessage) {
            KnownPeerHandler.handlePeerInfoRequest((PeerInfoMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {
        super.handleFailedMessage(message, peer);
        if (message.getTitle().equals("PeerInfoMessage")) {
            if (((PeerInfoMessage) message).getStatus().equals("Request")) {
                requestPeerInfo();
            }
        }
    }
}
