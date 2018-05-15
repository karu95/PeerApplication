package com.peerapplication.util;

import messenger.PeerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBConnectionTest {

    private static final DBConnection dbConnection = DBConnection.getDBConnection();

    @BeforeEach
    void setUp() {
        PeerHandler.setup(25035);
    }

    @Test
    void getDBConnection() {
        assertEquals(DBConnection.getDBConnection(), dbConnection);
    }

    @Test
    void getConnection() {
        dbConnection.getConnection();
    }

}