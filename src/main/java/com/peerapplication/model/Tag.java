package com.peerapplication.model;

import com.peerapplication.repository.TagRepository;

import java.util.ArrayList;

public class Tag {
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public static ArrayList<Tag> getTags(String threadID) {
        TagRepository tagRepository = TagRepository.getTagRepository();
        return tagRepository.getTags(threadID);
    }

    public static ArrayList<Tag> getAllTags() {
        TagRepository tagRepository = TagRepository.getTagRepository();
        return tagRepository.getAllTags();
    }

    public static void saveTags(ArrayList<Tag> tags, String threadID) {
        TagRepository tagRepository = TagRepository.getTagRepository();
        tagRepository.saveTags(tags, threadID);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
