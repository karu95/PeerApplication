package message;

public class LogoutMessage extends Message implements BSMessage{
    private int userID;

    public LogoutMessage(int userID){
        super("LogoutMessage");
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
