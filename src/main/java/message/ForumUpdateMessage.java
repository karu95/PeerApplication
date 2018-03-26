package message;

import com.peerapplication.model.Answer;
import com.peerapplication.model.User;
import com.peerapplication.model.Vote;

import java.util.ArrayList;

public class ForumUpdateMessage extends Message {

    private String status;
    private long lastSeen;
    private ArrayList<Thread> latestThreads;
    private ArrayList<User> registeredUsers;
    private ArrayList<Answer> latestAnswers;
    private ArrayList<Vote> latestVotes;

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
}
