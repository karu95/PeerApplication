package com.peerapplication.repository;

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

class TagRepositoryTest {

    private TagRepository tagRepository;
    private static String threadID;

    @BeforeAll
    static void init() {
        TableRepository tableRepository = new TableRepository();
        tableRepository.createTables();
        SystemUser.setSystemUserID(100333);
        long timestamp = new Date(System.currentTimeMillis()).getTime();
        threadID = IDGenerator.generateThreadID(timestamp);
        Thread thread = new Thread();
        thread.setThreadID(threadID);
        thread.setTitle("aasd");
        thread.setDescription("asdasdas");
        thread.setUserID(100333);
        thread.setTimestamp(timestamp);
        ThreadRepository.getThreadRepository().saveThread(thread);
    }

    @Test
    void getTagRepository() {
        assertEquals(TagRepository.getTagRepository(), TagRepository.getTagRepository());
    }

    @Test
    void getTagsValidThreadID() {
        assertEquals(TagRepository.getTagRepository().getTags(threadID).size(), 3);
    }

    @Test
    void getTagsInvalidThreadID() {
        assertEquals(TagRepository.getTagRepository().getTags("100000000").size(), 0);
    }

    @Test
    void getRelatedThreadsUnavailableTag() {
        String[] tags = new String[1];
        tags[0] = "bcx";
        assertTrue(TagRepository.getTagRepository().getRelatedThreads(tags).size() == 0);
    }

    @Test
    void getRelatedThreadsAvailableTag() {
        String[] searchTags = new String[1];
        searchTags[0] = "asd";
        assertTrue(TagRepository.getTagRepository().getRelatedThreads(searchTags).size() > 0);
    }

    @Test
    void saveTags() {
        SystemUser.setSystemUserID(100333);
        ArrayList<Tag> tags = new ArrayList<Tag>() {{
            add(new Tag("asd"));
            add(new Tag("abc"));
            add(new Tag("qwer"));
        }};
        TagRepository.getTagRepository().saveTags(tags, threadID);
    }

}