package Models;

/**
 * Represents a message within the system that is sent between two users (a sender and a receiver).
 * Each message has a unique ID, content, sender, and receiver.
 */
public class Message implements Identifiable {
    private int messageID;
    private String message;
    private Integer senderId;
    private Integer receiverId;

    /**
     * Constructs a new Message with the specified ID, content, senderId, and receiverId.
     *
     * @param messageID The unique identifier for this message.
     * @param message   The content of the message.
     * @param senderId    The user who sent the message.
     * @param receiverId  The user who is intended to receive the message.
     */
    public Message(int messageID, String message, Integer senderId, Integer receiverId) {
        this.messageID = messageID;
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    /**
     * Gets the content of the message.
     *
     * @return A string containing the message content.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the user who sent the message.
     *
     * @return The sender of the message as a User object.
     */
    public Integer getSenderId() {
        return this.senderId;
    }

    /**
     * Gets the user who is the intended receiver of the message.
     *
     * @return The receiver of the message as a User object.
     */
    public Integer getReceiverId() {
        return this.receiverId;
    }

    /**
     * Gets the unique identifier of the message.
     *
     * @return The message ID as an Integer.
     */
    @Override
    public Integer getId() {
        return this.messageID;
    }

    @Override
    public void setId(Integer newId){this.messageID = newId;}

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(Integer sender) {
        this.senderId = sender;
    }

    public void setReceiver(Integer receiver) {
        this.receiverId = receiver;
    }


    /**
     * Provides a string representation of the message, including the message ID, content,
     * sender, and receiver.
     *
     * @return A formatted string with the message details.
     */
    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", message='" + message + '\'' +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}