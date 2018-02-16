package com.peerapplication.message;

public class RegisterMessage extends Message{

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

    @Override
    public String messageToString() {
        return super.messageToString()+","+username+","+password+"\n";
    }

}
