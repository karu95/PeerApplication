package message;

public class JoinMessage extends Message {
    private String description;

    public JoinMessage(String description) {
        super("JoinMessage");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
