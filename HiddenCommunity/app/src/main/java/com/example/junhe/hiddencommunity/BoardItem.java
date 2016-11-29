package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-11-29.
 */

public class BoardItem {
    String BoardId;
    String Category;
    String Title;
    String Author;
    String Date;
    String Body;
    String Tag;
    int Hit;
    int Like;
    int Hate;

    public String getBoardId() { return BoardId; }
    public String getCategory() { return Category; }
    public String getTitle() { return Title; }
    public String getAuthor() { return Author; }
    public String getDate() { return Date; }
    public String getBody() { return Body; }
    public String getTag() { return Tag; }
    public int getHit() { return Hit; }
    public int getLike() { return Like; }
    public int getHate() { return Hate; }


    public BoardItem(String BoardId, String Category, String Title,  String Author, String Date, String Body, String Tag, int Hit, int Like, int Hate)
    {
        this.BoardId = BoardId;
        this.Category = Category;
        this.Title = Title;
        this.Author = Author;
        this.Date = Date;
        this.Body = Body;
        this.Tag = Tag;
        this.Hit = Hit;
        this.Like = Like;
        this.Hate = Hate;
    }
}
