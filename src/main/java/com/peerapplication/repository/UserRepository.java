package com.peerapplication.repository;

import com.peerapplication.model.User;
import com.peerapplication.util.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
                user.setLastProfileUpdate(rs.getLong("last_update_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        Connection connection = dbConn.getConnection();
        String insertStatement = "INSERT INTO users(user_name, email, register_time, image, last_update_time, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        String updateStatement = "UPDATE users SET user_name=?, email=?, register_time=?, image=?, last_update_time=? WHERE user_id=?";
        try {
            User user1 = new User();
            user1.getUser(user.getUserID());
            PreparedStatement stmt = null;
            if (user1.getUserID() == user.getUserID()) {
                stmt = connection.prepareStatement(updateStatement);
                System.out.println("User Updated");
            } else {
                stmt = connection.prepareStatement(insertStatement);
                System.out.println("User inserted!");
            }
            stmt.setInt(6, user.getUserID());
            stmt.setLong(5, user.getLastProfileUpdate());
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setLong(3, user.getRegisterTime());
            stmt.setString(4, user.getImageURL());
            readWriteLock.writeLock().lock();
            stmt.execute();
            readWriteLock.writeLock().unlock();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate User");
                return;
            }
            e.printStackTrace();
        }
    }

    public ArrayList<User> getLatestUsers(long timestamp) {
        Connection connection = dbConn.getConnection();
        ArrayList<User> latestUsers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE last_update_time>= ? ORDER BY last_update_time ASC";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setLong(1, timestamp);
            readWriteLock.readLock().lock();
            ResultSet rs = psmt.executeQuery();
            readWriteLock.readLock().unlock();
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("email"));
                user.setImageURL(rs.getString("image"));
                user.setRegisterTime(rs.getLong("register_time"));
                user.setLastProfileUpdate(rs.getLong("last_update_time"));
                user.getUserWithImage(user.getUserID());
                latestUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latestUsers;
    }
}
