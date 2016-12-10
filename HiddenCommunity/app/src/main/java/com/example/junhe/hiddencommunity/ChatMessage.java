package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-11-28.
 */

public class ChatMessage {
    public boolean left;
    public String recipient;
    public String message;
    public String sender;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        //this.recipient = recipient;
        this.message = message;
//        this.sender = sender;
    }

//    public void setRecipient(String recipient) { this.recipient = recipient; }
//    public String getRecipient() { return recipient; }

    public String getMessage(){
        return message;
    }

//    public void setSender(String sender) { this.sender = sender; }
//    public String getSender() { return sender; }

    public boolean getSide(){
        return left;
    }
}
