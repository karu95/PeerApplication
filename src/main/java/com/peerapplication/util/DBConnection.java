package com.peerapplication.util;

import messenger.PeerHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;

    private Properties prop;

    private DBConnection() {
        this.prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("properties/db.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getDBConnection() {
        if (dbConnection == null) {
            synchronized (DBConnection.class) {
                dbConnection = new DBConnection();
            }
        }
        return dbConnection;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(prop.getProperty("db.driver"));
            conn = DriverManager.getConnection(prop.getProperty("db.url") + String.valueOf(PeerHandler.getUserPort()) + ";create=true", prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
