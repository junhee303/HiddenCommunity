package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-11-28.
 */

public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public String getMessage(){
        return "getMessage";
    }
}
