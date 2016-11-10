package data;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class CommentData{
    private String Nickname;
    private String Date;
    private String Content;


    public CommentData(String Nickname, String Date, String Content) {
        this.Nickname = Nickname;
        this.Date = Date;
        this.Content = Content;
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

