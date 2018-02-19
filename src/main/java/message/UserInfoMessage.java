package message;

import com.peerapplication.model.User;

public class UserInfoMessage extends Message {
    private User user;

    public UserInfoMessage(){
        super("UserInfo");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
