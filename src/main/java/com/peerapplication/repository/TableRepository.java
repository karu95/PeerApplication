package com.peerapplication.repository;

import com.peerapplication.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepository {
    private DBConnection dbConn;

    public TableRepository(){
        dbConn = new DBConnection();
    }

    public void createTables(){
        Connection connection = dbConn.getConnection();
        String userTable = "CREATE TABLE users(" +
                "user_id (int) PRIMARY KEY," +
                "user_name VARCHAR (30)," +
                "email VARCHAR(30)," +
                "image VARCHAR (20))";
        String threadTableStmt = "CREATE TABLE thread (" +
                "thread_id VARCHAR (20) PRIMARY KEY," +
                "title VARCHAR(100)," +
                "description VARCHAR(2000)," +
                "posted_user (int)," +
                "FOREIGN KEY posted_user REFERENCES users.user_id)";
        String answerTable = "CREATE TABLE answer (" +
                "answer_id VARCHAR (20) PRIMARY KEY," +
                "description VARCHAR(2000)," +
                "posted_user (int)," +
                "related_thread VARCHAR (20)," +
                "FOREIGN KEY posted_user REFERENCES users.user_id," +
                "FOREIGN KEY related_thread REFERENCES thread.thread_id)";
        String tagTable = "CREATE TABLE tag(" +
                "tag VARCHAR (100) PRIMARY KEY)";
        String notificationTable="CREATE TABLE notification(" +
                ")";
        String votedTable="CREATE TABLE voted (" +
                "answer_id VARCHAR (20) PRIMARY KEY," +
                "user_id INT," +
                "FOREIGN KEY user_id REFERENCES users.user_id)";
        String deletedTable="CREATE TABLE deleted(" +
                "thread_id VARCHAR(20) PRIMARY KEY," +
                "user_id INT," +
                "FOREIGN KEY user_id REFERENCES users)";
        String taggedTable="CREATE TABLE tagged(" +
                "tag VARCHAR(100) PRIMARY KEY," +
                "thread_id VARCHAR(20)," +
                "FOREIGN KEY thread_id REFERENCES thread)";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(notificationTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
