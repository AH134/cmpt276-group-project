package ca.sfu.cmpt276.utils.chat;

public class ChatNotification {
    private int id;
    private int chatId;
    private int senderId;
    private int recipientId;
    private String content;

    public ChatNotification(int id, int chatId, int senderId, int recipientId, String content) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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
