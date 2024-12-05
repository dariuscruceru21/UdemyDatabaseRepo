package Models;

public class MessageForum {
    private Integer messageId;
    private Integer forumId;


    public MessageForum(Integer messageId, Integer forumId) {
        this.messageId = messageId;
        this.forumId = forumId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }
}
