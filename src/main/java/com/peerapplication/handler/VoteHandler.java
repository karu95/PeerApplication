package com.peerapplication.handler;

import com.peerapplication.model.Vote;
import com.peerapplication.notifcation.NotificationHandler;
import com.peerapplication.util.UIUpdateHandler;
import message.Message;
import message.VoteMessage;
import messenger.Handler;
import messenger.Peer;
import messenger.PeerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class VoteHandler extends Handler {

    private static VoteHandler voteHandler;
    private static ReadWriteLock voteHandleLock = new ReentrantReadWriteLock();

    private VoteHandler() {
    }

    public static VoteHandler getVoteHandler() {
        if (voteHandler == null) {
            synchronized (VoteHandler.class) {
                voteHandler = new VoteHandler();
            }
        }
        return voteHandler;
    }

    private static void handleVote(VoteMessage voteMessage) {
        System.out.println("Handling Vote");
        voteHandleLock.writeLock().lock();
        Vote vote = new Vote();
        vote.getVote(voteMessage.getVote().getAnswerID(), voteMessage.getVote().getUserID());
        if (!((vote.getUserID() == voteMessage.getVote().getUserID())
                && (vote.getAnswerID().equals(voteMessage.getVote().getAnswerID())))) {
            voteMessage.getVote().saveVote();
            NotificationHandler.getNotificationHandler().handleVote(voteMessage.getVote());
            UIUpdateHandler.informVoteUpdater(voteMessage);
            ArrayList<Peer> receivers = new ArrayList<>();
            PeerHandler.knownPeersReadLock();
            for (Map.Entry peer : PeerHandler.getKnownPeers().entrySet()) {
                if (peer.getKey().equals(Integer.valueOf(voteMessage.getVote().getUserID()))
                        || peer.getKey().equals(Integer.valueOf(voteMessage.getSenderID()))) {
                    System.out.println("Removed " + ((Peer) peer.getValue()).getUserID());
                    continue;
                } else {
                    receivers.add((Peer) peer.getValue());
                }
            }
            PeerHandler.knownPeersReadUnlock();
            PeerHandler.getSenderController().sendToAll(voteMessage, receivers);
            System.out.println("Vote detail Sent to known peers");
        }
        voteHandleLock.writeLock().unlock();

    }

    public void postVote(Vote vote) {
        System.out.println("Posting Vote");
        VoteMessage voteMessage = new VoteMessage();
        voteMessage.setVote(vote);
        PeerHandler.knownPeersReadLock();
        PeerHandler.getSenderController().sendToAll(voteMessage, new ArrayList<>(PeerHandler.getKnownPeers().values()));
        PeerHandler.knownPeersReadUnlock();
        System.out.println("Vote posted!");
    }

    @Override
    public void handle(Message message) {
        if (message instanceof VoteMessage) {
            VoteHandler.handleVote((VoteMessage) message);
        }
    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
