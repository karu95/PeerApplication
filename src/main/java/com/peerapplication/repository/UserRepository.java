package com.peerapplication.repository;

import com.peerapplication.model.User;
import com.peerapplication.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    DBConnection dbConn;

    public UserRepository(){
        dbConn = new DBConnection();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        return  users;
    }

    public void getUser(int userID, User user){
        Connection connection = dbConn.getConnection();
        String statement = "SELECT * FROM users WHERE user_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery(statement);
            while (rs.next()){
                user.setUserID(userID);
                user.setName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setImageURL(rs.getString("image"));
                user.setRegisterTime(rs.getLong("register_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user){

    }
}
