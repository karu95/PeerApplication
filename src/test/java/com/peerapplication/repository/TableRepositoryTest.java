package com.peerapplication.repository;

import org.junit.jupiter.api.Test;

class TableRepositoryTest {
    @Test
    void createTables() {
        TableRepository tableRepository = new TableRepository();
        tableRepository.createTables();
    }

}