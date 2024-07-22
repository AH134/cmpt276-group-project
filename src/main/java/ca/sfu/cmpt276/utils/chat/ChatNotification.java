package ca.sfu.cmpt276.utils.chat;

public class ChatNotification {
    private int id;
    private int senderId;
    private int recipientId;
    private String content;

    public ChatNotification(int id, int senderId, int recipientId, String content) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
