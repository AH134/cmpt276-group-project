package ca.sfu.cmpt276.utils.chat;

import java.time.LocalDate;

public class MessagePayload {
    private int chatId;
    private int senderId;
    private int recipientId;
    private String content;
    private LocalDate timestamp;

    public MessagePayload() {
    }

    public MessagePayload(int chatId, int senderId, int recipientId, String content, LocalDate timestamp) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getChatId() {
        return chatId;
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

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
