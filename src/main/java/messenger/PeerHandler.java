package messenger;

import com.peerapplication.handler.Handler;
import com.peerapplication.handler.UserHandler;
import message.Message;
import message.UserInfoMessage;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class PeerHandler {

    private static int userPort;
    private static String userAddress;
    private static HashMap<Integer, Peer> knownPeers = new HashMap<>();
    private static ReceiverController receiverController;
    private static SenderController senderController = new SenderController();
    private static Peer bs = new Peer(000000, "192.168.43.87", 25025);
    private static HeartBeatHandler heartBeatHandler = new HeartBeatHandler();
    private static HashMap<Class<Handler>, Class<Message>> hanlders = new HashMap<Class<Handler>, Class<Message>>();

    public static void registerHandler(Class<Handler> handlerType, Class<Message> messageType){
        hanlders.put(handlerType, messageType);
    }

    public static void hanldeMessage(Message message){
        hanlders.get(message.getClass());
    }

    public static HashMap<Integer, Peer> getKnownPeers() {
        return knownPeers;
    }

    public static void setKnownPeers(ArrayList<Peer> knownPeers) {
        HashMap<Integer, Peer> peers = new HashMap<>();
        if (knownPeers.isEmpty()){
            return;
        } else {
            for (Peer peer : knownPeers) {
                peers.put(peer.getUserID(), peer);
            }
        }
        PeerHandler.knownPeers = peers;
    }

    public static void addKnownPeer(Peer peer) {
        synchronized (knownPeers){
            knownPeers.put(peer.getUserID(), peer);
        }
    }

    public static void removeKnownPeer(Integer peerID) {
        synchronized (knownPeers){
            knownPeers.remove(peerID);
        }
    }

    public static String getUserAddress() {
        return userAddress;
    }

    public static int getUserPort() {
        return userPort;
    }

    public static void setup(int port) {
        PeerHandler.userPort = port;
        receiverController = new ReceiverController(userPort);
        PeerHandler.userAddress = getLocalIPAddress();
        Thread t = new Thread(receiverController);
        t.start();
    }

    public static String getLocalIPAddress() {
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
        Thread HBWorker = new Thread(heartBeatHandler);
        HBWorker.setDaemon(true);
        HBWorker.start();
    }

    public static void stopHeartBeat() {
        heartBeatHandler.setLoggedIn(false);
    }

    public static SenderController getSenderController() {
        return senderController;
    }

    public static Peer getBS() {
        return bs;
    }
}
