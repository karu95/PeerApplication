package com.peerapplication.handler;

import com.peerapplication.model.Thread;
import com.peerapplication.util.UIUpdateHandler;
import message.Message;
import message.ThreadMessage;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadHandler extends Handler {

    private static ThreadHandler threadHandler;
    private static ReadWriteLock threadHandleLock = new ReentrantReadWriteLock();

    private ThreadHandler() {
    }

    public static ThreadHandler getThreadHandler() {
        if (threadHandler == null) {
            synchronized (ThreadHandler.class) {
                threadHandler = new ThreadHandler();
            }
        }
        return threadHandler;
    }

    public static void postThread(Thread thread) {
        System.out.println("Posting thread");
        ThreadMessage threadMessage = new ThreadMessage();
        threadMessage.setThread(thread);
        PeerHandler.knownPeersReadLock();
        PeerHandler.getSenderController().sendToAll(threadMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
        PeerHandler.knownPeersReadUnlock();
        System.out.println("Thread posted");
    }

    private static void handleThread(ThreadMessage threadMessage) {
        System.out.println("thread message received");
        threadHandleLock.writeLock().lock();
        Thread thread = new Thread(threadMessage.getThread().getThreadID());
        if (thread.getThreadID() == null) {
            System.out.println("New Thread");
            threadMessage.getThread().saveThread();
            UIUpdateHandler.informThreadUpdater(threadMessage);
            ArrayList<Peer> receivers = new ArrayList<>();
            PeerHandler.knownPeersReadLock();
            for (Map.Entry peer : PeerHandler.getKnownPeers().entrySet()) {
                if (peer.getKey().equals(Integer.valueOf(threadMessage.getThread().getUserID()))
                        || peer.getKey().equals(Integer.valueOf(threadMessage.getSenderID()))) {
                    continue;
                } else {
                    receivers.add((Peer) peer.getValue());
                }
            }
            PeerHandler.knownPeersReadUnlock();
            PeerHandler.getSenderController().sendToAll(threadMessage, receivers);
            System.out.println("Thread detail Sent to known peers");
        }
        threadHandleLock.writeLock().unlock();
    }

    @Override
    public void handle(Message message) {
        if (message instanceof ThreadMessage) {
            ThreadHandler.handleThread((ThreadMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
