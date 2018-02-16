package com.peerapplication.repository;

import com.peerapplication.model.User;
import com.peerapplication.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    public User getUser(int userID){
        User user = null;

        return user;
    }

    public void setupTables(){
        Connection conn = dbConn.getConnection();
        String userTableStmt = "CREATE TABLE user_detail(" +
                "user_id int," +
                "name VARCHAR(20)," +
                "email VARCHAR (40) NOT NULL," +
                "PRIMARY KEY (user_id))";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(userTableStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
