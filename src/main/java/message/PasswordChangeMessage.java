package message;

public class PasswordChangeMessage extends Message implements BSMessage {
    public PasswordChangeMessage(String title) {
        super("PWChange");
    }
}
