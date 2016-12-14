package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-12-14.
 */

public class Notice {
    public String boardId;
    public String actionAuthor;
    public String type;
    public boolean check;
    public String date;

    public  Notice(String boardId, String actionAuthor, String type, boolean check, String date) {
        super();
        this.boardId = boardId;
        this.actionAuthor = actionAuthor;
        this.type = type;
        this.check =check;
        this.date = date;
    }

    public String getBoardId(){
        return boardId;
    }

    public void setActionAuthor(String actionAuthor) { this.actionAuthor = actionAuthor; }
    public String getActionAuthor() { return actionAuthor; }

    public String getType(){
        return type;
    }

    public void setCheck(boolean check) { this.check = check; }
    public boolean getCheck() { return check; }

    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }

}
