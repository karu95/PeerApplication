package messenger;

import com.peerapplication.util.SystemUser;
import message.BSMessage;
import message.Message;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PeerHandler {

    private static int userPort;
    private static String userAddress;
    private static HashMap<Integer, Peer> knownPeers = new HashMap<>();
    private static ReadWriteLock knownPeersLock = new ReentrantReadWriteLock();
    private static ReceiverController receiverController;
    private static SenderController senderController = new SenderController();
    private static Peer bs = new Peer();
    private static HeartBeatHandler heartBeatHandler = HeartBeatHandler.getHeartBeatHandler();
    private static ExecutorService serverWorker = Executors.newSingleThreadExecutor();
    private static HashMap<String, Handler> handlers = new HashMap<>();

    public static void registerHandler(String messageTitle, Handler handler) {
        handlers.putIfAbsent(messageTitle, handler);
    }

    static void handle(Message message) {
        if ((SystemUser.getSystemUserID() != 0) || ((message instanceof BSMessage))) {
            if (handlers.containsKey(message.getTitle())) {
                handlers.get(message.getTitle()).handle(message);
            } else if (message.getTitle().equals("HeartBeatSuccess")) {

            } else {
                System.out.println(message.getTitle());
                System.out.println("UnknownMessage");
            }
        } else {
            System.out.println("Not Logged IN");
        }
    }

    static void handleFailedMessage(Message message, Peer peer) {
        if (handlers.containsKey(message.getTitle())) {
            handlers.get(message.getTitle()).handleFailedMessage(message, peer);
        }
    }

    public static HashMap<Integer, Peer> getKnownPeers() {
        return knownPeers;
    }

    public static void setKnownPeers(ArrayList<Peer> knownPeers) {
        HashMap<Integer, Peer> peers = new HashMap<>();
        if (!knownPeers.isEmpty()) {
            for (Peer peer : knownPeers) {
                System.out.println("Peer ID " + peer.getUserID());
                peers.put(peer.getUserID(), peer);
            }
        }
        knownPeersWriteLock();
        PeerHandler.knownPeers = peers;
        knownPeersWriteUnlock();
        KnownPeerHandler.sendJoinMessageToAll();
        KnownPeerHandler.requestPeerInfo();
    }

    static void addKnownPeer(Peer peer) {
        knownPeersWriteLock();
        if (!knownPeers.containsKey(peer.getUserID())) {
            knownPeers.put(peer.getUserID(), peer);
        } else {
            knownPeers.replace(peer.getUserID(), peer);
        }
        knownPeersWriteUnlock();
    }

    static void removeKnownPeer(Integer peerID) {
        knownPeersWriteLock();
        if (knownPeers.containsKey(peerID)) {
            knownPeers.remove(peerID);
        }
        knownPeersWriteUnlock();
    }

    public static String getUserAddress() {
        return userAddress;
    }

    public static int getUserPort() {
        return userPort;
    }

    public static void setup(int port) {
        PeerHandler.userPort = port;
        System.out.println("Listening on " + port);
        receiverController = new ReceiverController(userPort);
        serverWorker.execute(receiverController);
        registerHandler("JoinMessage", KnownPeerHandler.getKnownPeerHandler());
        registerHandler("PeerInfoMessage", KnownPeerHandler.getKnownPeerHandler());
    }

    private static String getLocalIPAddress() {
        InetAddress inetAddress = null;
        try {
            for (
                    final Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
                    interfaces.hasMoreElements();
                    ) {
                final NetworkInterface cur = interfaces.nextElement();

                if (cur.isLoopback()) {
                    continue;
                }

                for (final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                    final InetAddress inet_addr = addr.getAddress();

                    if (!(inet_addr instanceof Inet4Address)) {
                        continue;
                    }
                    inetAddress = inet_addr;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return "No";
    }

    public static boolean checkConnection() {
        String localIPAddress = getLocalIPAddress();
        System.out.println("Connection Check " + localIPAddress);
        if (!localIPAddress.equals("No")) {
            if (userAddress == null) {
                userAddress = localIPAddress;
                return true;
            } else if (userAddress.equals(localIPAddress)) {
                return true;
            }
        }
        return false;
    }

    public static void startHeartBeat() {
        heartBeatHandler.startHeartBeat();
    }

    public static void stopHeartBeat() {
        heartBeatHandler.stop();
    }

    public static SenderController getSenderController() {
        return senderController;
    }

    public static Peer getBS() {
        return bs;
    }

    public static void knownPeersReadLock() {
        knownPeersLock.readLock().lock();
    }

    public static void knownPeersWriteLock() {
        knownPeersLock.writeLock().lock();
    }

    public static void knownPeersReadUnlock() {
        knownPeersLock.readLock().unlock();
    }

    public static void knownPeersWriteUnlock() {
        knownPeersLock.writeLock().unlock();
    }
}
