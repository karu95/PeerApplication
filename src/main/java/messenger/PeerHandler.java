package messenger;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class PeerHandler {

    private static int userPort;
    private static String userAddress;
    private static ArrayList<Peer> knownPeers = new ArrayList<>();
    private static ReceiverController receiverController;
    private static SenderController senderController = new SenderController();
    private static Peer bs = new Peer(000000, "192.168.8.100", 25025);
    private static HeartBeatHandler heartBeatHandler = new HeartBeatHandler();


    public static ArrayList<Peer> getKnownPeers() {
        return knownPeers;
    }

    public static void setKnownPeers(ArrayList<Peer> knownPeers) {
        PeerHandler.knownPeers = knownPeers;
    }

    public static void addKnownPeer(Peer peer) {
        synchronized (knownPeers) {
            knownPeers.add(peer);
        }
    }

    public static void removeKnownPeer(Peer peer) {
        synchronized (knownPeers) {
            knownPeers.remove(peer);
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
