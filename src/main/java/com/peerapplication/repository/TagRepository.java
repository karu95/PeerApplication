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

    public static TagRepository getTagRepository() {
        if (tagRepository == null) {
            synchronized (TagRepository.class) {
                tagRepository = new TagRepository();
            }
        }
        return tagRepository;
    }

    private TagRepository() {
        dbConnection = DBConnection.getDBConnection();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public ArrayList<Tag> getTags(String threadID) {
        Connection connection = dbConnection.getConnection();
        String getTagsQuery = "SELECT * FROM tagged WHERE thread_id=?";
        ArrayList<Tag> relatedTags = new ArrayList<>();
        try {
            PreparedStatement tagsPsmt = connection.prepareStatement(getTagsQuery);
            tagsPsmt.setString(1, threadID);
            ResultSet rs = tagsPsmt.executeQuery();
            while (rs.next()) {
                relatedTags.add(new Tag(rs.getString("tag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatedTags;
    }

    public ArrayList<Tag> getAllTags() {
        Connection connection = dbConnection.getConnection();
        ArrayList<Tag> tags = new ArrayList<>();
        String getAllQuery = "SELECT * FROM tag";
        try {
            PreparedStatement getAllPsmt = connection.prepareStatement(getAllQuery);
            ResultSet rs = getAllPsmt.executeQuery();
            while (rs.next()) {
                tags.add(new Tag(rs.getString("tag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    public void saveTags(ArrayList<Tag> tags, String threadID) {
        Connection connection = dbConnection.getConnection();
        String saveQuery = "INSERT INTO tag(tag) VALUES (?)";
        String saveTaggedRelation = "INSERT INTO tagged(tag, thread_id) VALUES (?, ?)";
        for (Tag tag : tags) {
            try {
                PreparedStatement savePsmt = connection.prepareStatement(saveQuery);
                savePsmt.setString(1, tag.getTag());
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    return;
                } else {
                    e.printStackTrace();
                }
            } finally {
                try {
                    PreparedStatement updateTaggedTableStmt = connection.prepareStatement(saveTaggedRelation);
                    updateTaggedTableStmt.setString(1, tag.getTag());
                    updateTaggedTableStmt.setString(2, threadID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
