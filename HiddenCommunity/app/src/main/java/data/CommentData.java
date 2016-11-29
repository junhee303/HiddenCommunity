package data;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class CommentData{
    private String BoardId;
    private String Author;
    private String Date;
    private String Body;


    public CommentData(String BoardId, String Author, String Date, String Body) {
        this.BoardId = BoardId;
        this.Author = Author;
        this.Date = Date;
        this.Body = Body;
    }

    public String getBoardId() { return BoardId; }
    public void setBoardId(String BoardId) {
        this.BoardId = BoardId;
    }

    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getDate() {
        return Date;
    }
    public void Date(String Date) {
        this.Date = Date;
    }

    public String getBody() {
        return Body;
    }
    public void setBody(String Body) {
        this.Body = Body;
    }
}

