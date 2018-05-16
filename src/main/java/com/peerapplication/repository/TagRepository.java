package com.peerapplication.repository;

import com.peerapplication.model.Tag;
import com.peerapplication.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TagRepository {

    private static TagRepository tagRepository;

    private DBConnection dbConnection;
    private ReadWriteLock readWriteLock;

    private TagRepository() {
        dbConnection = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public static TagRepository getTagRepository() {
        if (tagRepository == null) {
            synchronized (TagRepository.class) {
                tagRepository = new TagRepository();
            }
        }
        return tagRepository;
    }

    public ArrayList<Tag> getTags(String threadID) {
        Connection connection = dbConnection.getConnection();
        String getTagsQuery = "SELECT * FROM tagged WHERE thread_id=?";
        ArrayList<Tag> relatedTags = new ArrayList<>();
        try {
            PreparedStatement tagsPsmt = connection.prepareStatement(getTagsQuery);
            tagsPsmt.setString(1, threadID);
            readWriteLock.readLock().lock();
            ResultSet rs = tagsPsmt.executeQuery();
            while (rs.next()) {
                relatedTags.add(new Tag(rs.getString("tag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return relatedTags;
    }

    public ArrayList<String> getRelatedThreads(String[] searchedTags) {
        Connection connection = dbConnection.getConnection();
        ArrayList<String> threadIDs = new ArrayList<>();
        String getAllQuery = "SELECT thread_id FROM tagged WHERE tag = ?";
        try {
            PreparedStatement getAllPsmt = connection.prepareStatement(getAllQuery);
            readWriteLock.readLock().lock();
            for (String tag : searchedTags) {
                getAllPsmt.setString(1, tag);
                ResultSet rs = getAllPsmt.executeQuery();
                while (rs.next()) {
                    threadIDs.add(rs.getString("thread_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return threadIDs;
    }

    public void saveTags(ArrayList<Tag> tags, String threadID) {
        Connection connection = dbConnection.getConnection();
        String saveQuery = "INSERT INTO tag(tag) VALUES (?)";
        String saveTaggedRelation = "INSERT INTO tagged(tag, thread_id) VALUES (?, ?)";
        readWriteLock.writeLock().lock();
        for (Tag tag : tags) {
            try {
                PreparedStatement savePsmt = connection.prepareStatement(saveQuery);
                savePsmt.setString(1, tag.getTag());
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Duplicate tag!");
                } else {
                    e.printStackTrace();
                }
            } finally {
                try {
                    PreparedStatement updateTaggedTableStmt = connection.prepareStatement(saveTaggedRelation);
                    updateTaggedTableStmt.setString(1, tag.getTag());
                    updateTaggedTableStmt.setString(2, threadID);
                    updateTaggedTableStmt.execute();
                } catch (SQLException e) {
                    if (e instanceof SQLIntegrityConstraintViolationException) {
                        System.out.println("Integrity Constraint");
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        }
        readWriteLock.writeLock().unlock();
    }
}