package com.peerapplication.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadRepositoryTest {

    private static final ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
    private ThreadRepository newThreadRepository;

    @BeforeEach
    void setUp() {
        newThreadRepository = ThreadRepository.getThreadRepository();
    }

    @Test
    void getThreadRepository() {
        assertEquals(newThreadRepository, threadRepository);
    }

    @Test
    void getThread() {

    }

    @Test
    void saveThread() throws Exception {

    }

    @Test
    void getThreads() {
    }

    @Test
    void getLatestThreads() {
    }

    @Test
    void deleteThread() {
    }

    @Test
    void getDeletedThreads() {
    }

    @Test
    void checkDeleted() {
    }

}