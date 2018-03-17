package com.peerapplication.repository;

import com.peerapplication.model.User;
import com.peerapplication.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserRepository {

    private static UserRepository userRepository;

    private DBConnection dbConn;
    private ReadWriteLock readWriteLock;

    private UserRepository() {
        dbConn = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public static UserRepository getUserRepository() {
        if (userRepository == null) {
            synchronized (UserRepository.class) {
                userRepository = new UserRepository();
            }
        }
        return userRepository;
    }

    public void getUser(int userID, User user) {
        Connection connection = dbConn.getConnection();
        String statement = "SELECT * FROM users WHERE user_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setInt(1, userID);
            readWriteLock.readLock().lock();
            ResultSet rs = stmt.executeQuery();
            readWriteLock.readLock().unlock();
            while (rs.next()) {
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

    public void saveUser(User user) {
        Connection connection = dbConn.getConnection();
        String statement = "INSERT INTO users(user_id, user_name, email, register_time, image) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setLong(4, user.getRegisterTime());
            stmt.setString(5, user.getImageURL());
            readWriteLock.writeLock().lock();
            stmt.execute();
            readWriteLock.writeLock().unlock();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
