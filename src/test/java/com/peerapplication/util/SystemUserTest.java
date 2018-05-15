package com.peerapplication.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemUserTest {
    @Test
    void testLastSeen() {
        SystemUser.setTablesCreated(false);
        SystemUser.setLastSeen(10000);
        assertEquals(SystemUser.getLastSeen(), 10000);
        SystemUser.setLastSeen(0);
        SystemUser.setTablesCreated(true);
        SystemUser.setLastSeen(1500);
        assertEquals(SystemUser.getLastSeen(), 0);
    }
}