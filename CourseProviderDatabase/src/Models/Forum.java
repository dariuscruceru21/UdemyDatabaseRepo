
package Models;

import java.util.Arrays;

/**
 * Represents a forum in the system where users can participate in discussions based on a specific topic.
 * The forum has a unique ID, a main topic, and a list of related topics.
 */
public class Forum implements Identifiable {
    private Integer forumID;
    private String topic;
    private String[] topics;

    /**
     * Constructs a new Forum with the specified forum ID, main topic, and related topics.
     *
     * @param forumID The unique identifier for this forum.
     * @param topic   The main topic of the forum.
     * @param topics  An array of topics related to the forum discussion.
     */
    public Forum(int forumID, String topic, String[] topics) {
        this.forumID = forumID;
        this.topic = topic;
        this.topics = topics;
    }

    public Integer getForumID() {
        return forumID;
    }

    public void setForumID(Integer forumID) {
        this.forumID = forumID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    @Override
    public Integer getId() {
        return this.forumID;
    }

    @Override
    public void setId(Integer id) {
        this.forumID = id;
    }
}
