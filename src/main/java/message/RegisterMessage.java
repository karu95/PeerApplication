package message;

public class RegisterMessage extends Message implements BSMessage{

    private String username;
    private String password;

    public RegisterMessage(){super("Register");}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
