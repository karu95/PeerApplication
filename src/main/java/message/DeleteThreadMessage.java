package message;

public class DeleteThreadMessage extends Message {

    private String threadID;
    private int deletedUser;

    public DeleteThreadMessage() {
        super("DeleteThreadMessage");
    }


    public int getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(int deletedUser) {
        this.deletedUser = deletedUser;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
}
