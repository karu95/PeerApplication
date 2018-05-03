package message;

import com.peerapplication.model.*;
import com.peerapplication.model.Thread;

import java.util.ArrayList;

public class ForumUpdateMessage extends Message {

    private String status;
    private long lastSeen;
    private ArrayList<Thread> latestThreads;
    private ArrayList<User> registeredUsers;
    private ArrayList<Answer> latestAnswers;
    private ArrayList<Vote> latestVotes;
    private ArrayList<DeletedThread> deletedThreads;

    public ForumUpdateMessage() {
        super("ForumUpdateMessage");
    }


    public ArrayList<Thread> getLatestThreads() {
        return latestThreads;
    }

    public void setLatestThreads(ArrayList<Thread> latestThreads) {
        this.latestThreads = latestThreads;
    }

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public ArrayList<Answer> getLatestAnswers() {
        return latestAnswers;
    }

    public void setLatestAnswers(ArrayList<Answer> latestAnswers) {
        this.latestAnswers = latestAnswers;
    }

    public ArrayList<Vote> getLatestVotes() {
        return latestVotes;
    }

    public void setLatestVotes(ArrayList<Vote> latestVotes) {
        this.latestVotes = latestVotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public ArrayList<DeletedThread> getDeletedThreads() {
        return deletedThreads;
    }

    public void setDeletedThreads(ArrayList<DeletedThread> deletedThreads) {
        this.deletedThreads = deletedThreads;
    }
}
