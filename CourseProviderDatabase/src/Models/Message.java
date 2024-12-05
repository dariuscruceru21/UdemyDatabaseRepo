package Models;

/**
 * Represents a message within the system that is sent between two users (a sender and a receiver).
 * Each message has a unique ID, content, sender, and receiver.
 */
public class Message implements Identifiable {
    private int messageID;
    private String message;
    private User sender;
    private User receiver;

    /**
     * Constructs a new Message with the specified ID, content, sender, and receiver.
     *
     * @param messageID The unique identifier for this message.
     * @param message   The content of the message.
     * @param sender    The user who sent the message.
     * @param receiver  The user who is intended to receive the message.
     */
    public Message(int messageID, String message, User sender, User receiver) {
        this.messageID = messageID;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
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
    public User getSender() {
        return this.sender;
    }

    /**
     * Gets the user who is the intended receiver of the message.
     *
     * @return The receiver of the message as a User object.
     */
    public User getReceiver() {
        return this.receiver;
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


    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}