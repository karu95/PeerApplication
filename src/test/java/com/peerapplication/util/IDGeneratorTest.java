package com.peerapplication.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IDGeneratorTest {

    private long timestamp;

    @BeforeEach
    void setUp() {
        SystemUser.setSystemUserID(100);
        timestamp = new Date(System.currentTimeMillis()).getTime();
    }

    @Test
    void generateThreadID() {
        assertEquals(IDGenerator.generateThreadID(timestamp), "1000" + String.valueOf(timestamp));
    }

    @Test
    void generateAnswerID() {
        assertEquals(IDGenerator.generateAnswerID(timestamp), "1001" + String.valueOf(timestamp));
    }
}