package Models;

import java.io.Serializable;

public class MessageForum implements Identifiable, Serializable {
    private Integer messageId;
    private Integer forumId;

    public MessageForum(Integer messageId, Integer forumId) {
        this.messageId = messageId;
        this.forumId = forumId;
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    @Override
    public Integer getId() {
        return messageId;
    }

    @Override
    public void setId(Integer id) {
        this.messageId = id;
    }
}
