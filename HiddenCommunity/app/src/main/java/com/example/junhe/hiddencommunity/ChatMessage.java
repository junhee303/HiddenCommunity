package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-11-28.
 */

public class ChatMessage {
    public boolean left;
    public String recipient;
    public String message;
    public String sender;
    public String date;

    public ChatMessage(boolean left, String recipient, String message, String sender, String date) {
        super();
        this.left = left;
        this.recipient = recipient;
        this.message = message;
        this.sender = sender;
        this.date = date;
    }

    public boolean getSide(){
        return left;
    }

    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getRecipient() { return recipient; }

    public String getMessage(){
        return message;
    }

    public void setSender(String sender) { this.sender = sender; }
    public String getSender() { return sender; }

    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }

}
