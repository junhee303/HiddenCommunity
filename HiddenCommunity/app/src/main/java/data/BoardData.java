package data;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class BoardData{
    private String Title;
    private String Nickname;
    private String Date;
    private String Content;
    private String Tag;
    private String count_hit; // 조회 수
    private String count_like; // 좋아요 수
    private String count_comment; // 댓글 수수
    public BoardData(String Title,  String Nickname, String Date, String Content) {
        this.Title = Title;
        this.Nickname = Nickname;
        this.Date = Date;
        this.Content = Content;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getNickname() {
        return Nickname;
    }
    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getDate() {
        return Date;
    }
    public void Date(String Date) {
        this.Date = Date;
    }

    public String getContent() {
        return Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
}

