package messenger;

import com.peerapplication.handler.Handler;
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
    private static Peer bs = new Peer(1, "192.168.8.102", 25025);
    private static HeartBeatHandler heartBeatHandler = new HeartBeatHandler();
    private static ExecutorService heartbeatExecutor = Executors.newSingleThreadExecutor();
    private static ExecutorService serverWorker = Executors.newSingleThreadExecutor();
    private static HashMap<String, Handler> handlers = new HashMap<>();

    public static void registerHandler(String messageTitle, Handler handler) {
        handlers.putIfAbsent(messageTitle, handler);
    }

    static void handle(Message message) {
        if (handlers.containsKey(message.getTitle())) {
            handlers.get(message.getTitle()).handle(message);
        }
    }

    public static HashMap<Integer, Peer> getKnownPeers() {
        return knownPeers;
    }

    public static void setKnownPeers(ArrayList<Peer> knownPeers) {
        HashMap<Integer, Peer> peers = new HashMap<>();
        if (knownPeers.isEmpty()) {
            return;
        } else {
            for (Peer peer : knownPeers) {
                peers.put(peer.getUserID(), peer);
            }
        }
        knownPeersWriteLock();
        PeerHandler.knownPeers = peers;
        knownPeersWriteUnlock();
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

    static String getUserAddress() {
        return userAddress;
    }

    static int getUserPort() {
        return userPort;
    }

    public static void setup(int port) {
        PeerHandler.userPort = port;
        receiverController = new ReceiverController(userPort);
        PeerHandler.userAddress = getLocalIPAddress();
        serverWorker.execute(receiverController);
    }

    static String getLocalIPAddress() {
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
                    System.out.println(
                            "  address: " + inet_addr.getHostAddress() +
                                    "/" + addr.getNetworkPrefixLength()
                    );
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return "No network Connection!";
    }

    public static void startHeartBeat() {
        heartBeatHandler.start();
        heartbeatExecutor.execute(heartBeatHandler);
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
