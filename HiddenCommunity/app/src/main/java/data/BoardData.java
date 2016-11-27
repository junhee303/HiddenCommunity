package data;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class BoardData{
    private String Category;
    private String Title;
    private String Author;
    private String Date;
    private String Body;
    private String Tag;
    private int count_hit; // 조회 수
    private int count_like; // 좋아요 수
    private int count_comment; // 댓글 수

    public BoardData(){

    }
    public BoardData(String Category, String Title,  String Author, String Date, String Body, String Tag, int count_hit, int count_like, int count_comment) {
        this.Category = Category;
        this.Title = Title;
        this.Author = Author;
        this.Date = Date;
        this.Body = Body;
        this.Tag = Tag;
        this.count_hit = count_hit;
        this.count_like = count_like;
        this.count_comment = count_comment;
    }

    public String getCategory() { return Category; }
    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() { return Author; }
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getBody() { return Body; }
    public void setBody(String Body) {
        this.Body = Body;
    }

    public String getTag() { return Tag;}
    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public int getHit() { return count_hit;}
    public void setHit(int count_hit) {
        this.count_hit = count_hit;
    }

    public int getLike() { return count_like;}
    public void setLike(int count_like) {
        this.count_like = count_like;
    }

    public int getComment() { return count_comment;}
    public void setComment(int count_comment) {
        this.count_comment = count_comment;
    }
}

