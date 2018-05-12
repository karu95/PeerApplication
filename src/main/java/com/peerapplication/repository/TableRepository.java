package com.peerapplication.repository;

import com.peerapplication.util.DBConnection;
import com.peerapplication.util.SystemUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepository {
    private DBConnection dbConn;

    public TableRepository() {
        dbConn = DBConnection.getDBConnection();
    }

    public void createTables() {
        Connection connection = dbConn.getConnection();
        String userTable = "CREATE TABLE users(" +
                "user_id INT PRIMARY KEY," +
                "user_name VARCHAR (30)," +
                "email VARCHAR (30)," +
                "register_time BIGINT," +
                "last_update_time BIGINT," +
                "image VARCHAR (30))";
        String threadTableStmt = "CREATE TABLE thread(" +
                "thread_id VARCHAR (20) PRIMARY KEY," +
                "title VARCHAR(100)," +
                "description VARCHAR(2000)," +
                "posted_time BIGINT," +
                "posted_user INT," +
                "FOREIGN KEY (posted_user) REFERENCES users(user_id))";
        String answerTable = "CREATE TABLE answer(" +
                "answer_id VARCHAR (20) PRIMARY KEY," +
                "description VARCHAR(2000)," +
                "posted_user INT," +
                "posted_time BIGINT," +
                "related_thread VARCHAR (20)," +
                "FOREIGN KEY (posted_user) REFERENCES users(user_id)," +
                "FOREIGN KEY (related_thread) REFERENCES thread(thread_id))";
        String tagTable = "CREATE TABLE tag(" +
                "tag VARCHAR (100) PRIMARY KEY)";
        String votedTable = "CREATE TABLE voted(" +
                "answer_id VARCHAR (20)," +
                "user_id INT," +
                "voted_time BIGINT," +
                "PRIMARY KEY(answer_id, user_id)," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id))";
        String deletedTable = "CREATE TABLE deleted(" +
                "thread_id VARCHAR(20) PRIMARY KEY," +
                "user_id INT," +
                "delete_time BIGINT," +
                "FOREIGN KEY (user_id) REFERENCES users)";
        String taggedTable = "CREATE TABLE tagged(" +
                "tag VARCHAR(100)," +
                "thread_id VARCHAR(20)," +
                "PRIMARY KEY(tag, thread_id)," +
                "FOREIGN KEY (thread_id) REFERENCES thread)";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(userTable);
            stmt.execute(threadTableStmt);
            stmt.execute(answerTable);
            stmt.execute(tagTable);
            stmt.execute(votedTable);
            stmt.execute(deletedTable);
            stmt.execute(taggedTable);
            stmt.close();
            connection.close();
            SystemUser.setTablesCreated(true);
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("Tables created");
                return;
            }
            e.printStackTrace();
        }


    }
}
