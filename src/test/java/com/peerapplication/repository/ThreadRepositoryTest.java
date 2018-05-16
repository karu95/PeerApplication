package com.peerapplication.repository;

import com.peerapplication.model.DeletedThread;
import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreadRepositoryTest {

    private static final ThreadRepository threadRepository = ThreadRepository.getThreadRepository();
    private static String threadID;
    private static DeletedThread deletedThread;

    @BeforeAll
    static void init() {
        SystemUser.setSystemUserID(100333);
        threadID = IDGenerator.generateThreadID(new Date(System.currentTimeMillis()).getTime());
        TableRepository tableRepository = new TableRepository();
        tableRepository.createTables();
        deletedThread = new DeletedThread(threadID, 100333, new Date(System.currentTimeMillis()).getTime());
    }

    @Test
    void getThreadRepository() {
        assertEquals(ThreadRepository.getThreadRepository(), threadRepository);
    }


    @Test
    void getThread() {
        String newThreadID = IDGenerator.generateThreadID(new Date(System.currentTimeMillis()).getTime());
        Thread thread = new Thread();
        thread.setThreadID(newThreadID);
        thread.setUserID(100333);
        thread.setTitle("how are you?");
        thread.setDescription("fine");
        thread.setTimestamp(100000);
        threadRepository.saveThread(thread);
        Thread newThread = new Thread();
        threadRepository.getThread(newThreadID, newThread);
        assertEquals(newThread.getThreadID(), newThreadID);
    }

    @Test
    void saveThreadWithoutTags() throws Exception {
        Thread thread = new Thread();
        thread.setThreadID(threadID);
        thread.setUserID(100333);
        thread.setTitle("how are you?");
        thread.setDescription("fine");
        thread.setTimestamp(100000);
        threadRepository.saveThread(thread);
    }

    @Test
    void saveThreadWithTags() throws Exception {
        Thread thread = new Thread();
        thread.setTags(new ArrayList<Tag>() {{
            add(new Tag("hi"));
            add(new Tag("you"));
            add(new Tag("me"));
        }});
        thread.setThreadID(IDGenerator.generateThreadID(new Date(System.currentTimeMillis()).getTime()));
        thread.setUserID(100333);
        thread.setTitle("how are you?");
        thread.setDescription("fine");
        thread.setTimestamp(100000);
        threadRepository.saveThread(thread);
    }

    @Test
    void getThreads() {
        assertTrue(threadRepository.getThreads(100333).size() > 0);
        assertTrue(threadRepository.getThreads(900).size() == 0);
    }

    @Test
    void getLatestThreads() {
        assertTrue(threadRepository.getLatestThreads(0).size() > 0);
        assertTrue(threadRepository.getLatestThreads(new Date(System.currentTimeMillis()).getTime()).size() == 0);
    }

    @Test
    void deleteThread() {
        threadRepository.deleteThread(deletedThread);
    }

    @Test
    void getDeletedThreads() {
        assertTrue(threadRepository.getDeletedThreads(0).size() > 0);
        assertTrue(threadRepository.getDeletedThreads(new Date(System.currentTimeMillis()).getTime()).size() == 0);
    }

    @Test
    void checkDeleted() {
        threadRepository.deleteThread(deletedThread);
        assertTrue(threadRepository.checkDeleted(deletedThread));
    }

}