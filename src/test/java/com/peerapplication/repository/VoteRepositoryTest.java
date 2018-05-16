package com.peerapplication.repository;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.model.Vote;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VoteRepositoryTest {

    private static Thread thread;
    private Answer answer;

    @BeforeAll
    static void init() {
        SystemUser.setSystemUserID(100349);
        thread = new Thread();
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
        ThreadRepository.getThreadRepository().saveThread(thread);
    }

    @BeforeEach
    void setUp() {
        answer = new Answer();
        answer.setThreadID(thread.getThreadID());
        answer.setDescription("thank you thank you");
        answer.setPostedUserID(SystemUser.getSystemUserID());
        answer.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        answer.setAnswerID(IDGenerator.generateAnswerID(answer.getTimestamp()));
    }

    @Test
    void getVoteRepository() {
        assertEquals(VoteRepository.getVoteRepository(), VoteRepository.getVoteRepository());
    }

    @Test
    void getVote() {
        VoteRepository.getVoteRepository().saveVote(new Vote(100333, answer.getAnswerID(), new Date(System.currentTimeMillis()).getTime()));
        assertTrue(VoteRepository.getVoteRepository().getVote(answer.getAnswerID(), 100333, new Vote()).getAnswerID().equals(answer.getAnswerID()));
    }

    @Test
    void saveVote() {
        VoteRepository.getVoteRepository().saveVote(new Vote(100333, answer.getAnswerID(), new Date(System.currentTimeMillis()).getTime()));
    }

    @Test
    void getVotes() {
        VoteRepository.getVoteRepository().saveVote(new Vote(100333, answer.getAnswerID(), new Date(System.currentTimeMillis()).getTime()));
        VoteRepository.getVoteRepository().saveVote(new Vote(100349, answer.getAnswerID(), new Date(System.currentTimeMillis()).getTime()));
        ;
        assertTrue(VoteRepository.getVoteRepository().getVotes(answer.getAnswerID()).size() == 2);
    }

    @Test
    void getLatestVotes() {
        assertTrue(VoteRepository.getVoteRepository().getLatestVotes(0).size() > 0);
        assertTrue(VoteRepository.getVoteRepository().getLatestVotes(new Date(System.currentTimeMillis()).getTime()).size() == 0);
    }
}