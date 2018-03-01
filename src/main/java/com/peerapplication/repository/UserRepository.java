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
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                user.setUserID(rs.getInt("user_id"));
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
        Connection connection = dbConn.getConnection();
        String statement = "INSERT INTO users(user_id, user_name, email, register_time, image) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setLong(4, user.getRegisterTime());
            stmt.setString(5, user.getImageURL());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
