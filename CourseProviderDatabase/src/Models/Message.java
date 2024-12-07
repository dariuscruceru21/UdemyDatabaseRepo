package Models;

/**
 * Represents a message within the system that is sent between two users (a sender and a receiver).
 * Each message has a unique ID, content, sender, and receiver.
 */
public class Message implements Identifiable {
    private int messageID;
    private String messagecontent;
    private Integer senderid;
    private Integer receiverid;

    /**
     * Constructs a new Message with the specified ID, content, sender, and receiver.
     *
     * @param messageID The unique identifier for this message.
     * @param messagecontent   The content of the message.
     * @param senderid    The user who sent the message.
     * @param receiverid  The user who is intended to receive the message.
     */
    public Message(int messageID, String messagecontent, Integer senderid, Integer receiverid) {
        this.messageID = messageID;
        this.messagecontent = messagecontent;
        this.senderid = senderid;
        this.receiverid = receiverid;
    }

    /**
     * Gets the content of the message.
     *
     * @return A string containing the message content.
     */
    public String getMessage() {
        return this.messagecontent;
    }

    /**
     * Gets the user who sent the message.
     *
     * @return The sender of the message as a User object.
     */
    public Integer getSender() {
        return this.senderid;
    }

    /**
     * Gets the user who is the intended receiver of the message.
     *
     * @return The receiver of the message as a User object.
     */
    public Integer getReceiver() {
        return this.receiverid;
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

    public void setMessage(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public void setSender(Integer senderid) {
        this.senderid = senderid;
    }

    public void setReceiver(Integer receiverid) {
        this.receiverid = receiverid;
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
                ", message='" + messagecontent + '\'' +
                ", sender=" + senderid +
                ", receiver=" + receiverid +
                '}';
    }
}