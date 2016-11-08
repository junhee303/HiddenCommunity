package data;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YelloMobile on 2015. 4. 8..
 */

public class BoardData{
    private TextView Title;
    private TextView Nickname;
    private TextView Date;
    private TextView Content;
    private TextView Tag;
    private TextView count_hit;
    private TextView count_like;
    private TextView count_comment;
    private TextView Comment;

    public BoardData(TextView Title,  TextView Nickname, TextView Date, TextView Content) {
        this.Title = Title;
        this.Nickname = Nickname;
        this.Date = Date;
        this.Content = Content;
    }

    public TextView getTitle() {
        return Title;
    }
    public void setTitle(EditText Title) {
        this.Title = Title;
    }

    public TextView getNickname() {
        return Nickname;
    }
    public void setNickname(TextView Nickname) {
        this.Nickname = Nickname;
    }

    public TextView getDate() {
        return Date;
    }
    public void Date(TextView Date) {
        this.Date = Date;
    }

    public TextView getContent() {
        return Content;
    }
    public void setContent(EditText Content) {
        this.Content = Content;
    }
}

