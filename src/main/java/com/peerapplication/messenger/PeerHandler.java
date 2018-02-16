package com.peerapplication.messenger;

import com.peerapplication.handler.BSHandler;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class PeerHandler {

    private static int userPort;
    private static String userAddress;
    private static ArrayList<Peer> knownPeers = new ArrayList<>();
    private static BSHandler bsHandler = new BSHandler();
    private static ReceiverController receiverController;
    private static SenderController senderController = new SenderController();
    private static int bsPort = 25025;
    private static int bsAddress;


    public static ArrayList<Peer> getKnownPeers() {
        return knownPeers;
    }

    public static void setKnownPeers(ArrayList<Peer> knownPeers) {
        PeerHandler.knownPeers = knownPeers;
    }

    public static BSHandler getBsHandler() {
        return bsHandler;
    }

    public static String getUserAddress() {
        return userAddress;
    }

    public static int getUserPort() {
        return userPort;
    }

    public static void setup(int port){
        PeerHandler.userPort = port;
        receiverController = new ReceiverController(userPort);
        PeerHandler.userAddress = getLocalIPAddress();
        Thread t = new Thread(receiverController);
        t.start();
    }

    public static String getLocalIPAddress(){
        try {
            for (
                    final Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
                    interfaces.hasMoreElements( );
                    )
            {
                final NetworkInterface cur = interfaces.nextElement( );

                if ( cur.isLoopback( ) )
                {
                    continue;
                }

                for ( final InterfaceAddress addr : cur.getInterfaceAddresses( ) )
                {
                    final InetAddress inet_addr = addr.getAddress( );

                    if ( !( inet_addr instanceof Inet4Address) )
                    {
                        continue;
                    }

                    return inet_addr.getHostAddress();
                    System.out.println(
                            "  address: " + inet_addr.getHostAddress( ) +
                                    "/" + addr.getNetworkPrefixLength( )
                    );
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
