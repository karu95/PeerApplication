package com.peerapplication.repository;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerRepositoryTest {

    private static Thread thread;
    private Answer answer;


    @BeforeAll
    static void init() {
        SystemUser.setSystemUserID(100349);
        User user = new User();
        user.setName("asda");
        user.setEmail("asda@w.com");
        user.setUserID(100349);
        user.setLastProfileUpdate(0);
        user.setRegisterTime(0);
        user.saveUser();
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
    void getAnswerRepository() {
        assertEquals(AnswerRepository.getAnswerRepository(), AnswerRepository.getAnswerRepository());
    }

    @Test
    void getAnswer() {
        AnswerRepository.getAnswerRepository().saveAnswer(answer);
        Answer newAnswer = new Answer();
        AnswerRepository.getAnswerRepository().getAnswer(answer.getAnswerID(), newAnswer);
        assertTrue(newAnswer.getAnswerID().equals(answer.getAnswerID()));
    }

    @Test
    void saveAnswer() {
        AnswerRepository.getAnswerRepository().saveAnswer(answer);
    }

    @Test
    void getAnswersOfThread() {
        AnswerRepository.getAnswerRepository().saveAnswer(answer);
        assertTrue(AnswerRepository.getAnswerRepository().getAnswers(thread.getThreadID()).size() > 0);
        assertTrue(AnswerRepository.getAnswerRepository().getAnswers("1000002312301").size() == 0);
    }

    @Test
    void getLatestAnswers() {
        assertTrue(AnswerRepository.getAnswerRepository().getLatestAnswers(new Date(System.currentTimeMillis()).getTime()).size() == 0);
        assertTrue(AnswerRepository.getAnswerRepository().getLatestAnswers(0).size() > 0);
    }

}