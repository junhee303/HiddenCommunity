package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.CommentData;

public class BoardReadingListViewActivity extends AppCompatActivity {

    private TextView select_board;
    private TextView TitleOfWriting;
    private TextView Writer;
    private TextView DateOfWriting;
    private TextView ContentOfWriting;
    private TextView TagOfWriting;
    private Button bLikeOn;
    private Button bLikeOff;
    private Button bAddComment;
    private TextView TheNumberOfLike;
    private boolean click_like = false;
    private int count_like = 0;
    private int count_comment = 0;

    ListView comment_list;
    EditText comment_edit;
    Comment_Adapter ca;
    ArrayList<CommentData> mCommentData = new ArrayList<CommentData>();
    View activity_board_reading; // header
    View comment_write; //footer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_reading_list_view);

        init();
    }

    public void init() {

        comment_list = (ListView) findViewById(R.id.jrv_comment_list);

        //핵심 부분!
        activity_board_reading = getLayoutInflater().inflate(R.layout.activity_board_reading, null, false);
        comment_write = getLayoutInflater().inflate(R.layout.comment_write, null, false);
        comment_list.addFooterView(activity_board_reading); // header
        comment_list.addFooterView(comment_write); // footer

        setTest();
        //setList(); // 댓글 ListView 세팅
        setHeader(); // activity_board_reading 세팅
       // setFooter(); // comment_write 세팅
    }

    private void setTest() {
        //의미없는 소스. (댓글 수 처음에 늘려놓기 위한 것)

        CommentData cd = new CommentData("댓글이 존재하지 않습니다","2016-00-00","운영자");
        mCommentData.add(cd);

        cd = new CommentData("테스트를 위해 댓글을 다는겁니다.","2016-00-00","운영자");
        mCommentData.add(cd);

        cd = new CommentData("댓글 테스트 댓글 테스트","2016-00-00","조주니");
        mCommentData.add(cd);

        cd = new CommentData("엽떡 먹고 싶다","2016-00-00","엽기닭도리도");
        mCommentData.add(cd);

    }

    private void setHeader() {
//        헤더의 id 값을 받아오기 위해서는 평소에 findViewById를 바로 썻는데
//        그 앞에 header.을 붙여서 header에 만들어 놓은 TextView의 아이디 값을 쓰겠다
//        이런 식으로 앞에 꼭 붙여주셔야 합니다. 안그러면 id값을 받아 올 수가 없어요 ㅠㅠ


        select_board = (TextView) activity_board_reading.findViewById(R.id.select_board);
        TitleOfWriting = (TextView) activity_board_reading.findViewById(R.id.TitleOfWriting);
        Writer = (TextView) activity_board_reading.findViewById(R.id.Writer);
        DateOfWriting = (TextView) activity_board_reading.findViewById(R.id.DateOfWriting);
        ContentOfWriting = (TextView) activity_board_reading.findViewById(R.id.ContentOfWriting);
        TagOfWriting = (TextView) activity_board_reading.findViewById(R.id.TagOfWriting);
        bLikeOn = (Button) activity_board_reading.findViewById(R.id.bLikeOn);
        bLikeOff = (Button) activity_board_reading.findViewById(R.id.bLikeOff);
        bAddComment = (Button) activity_board_reading.findViewById(R.id.bAddComment);
        TheNumberOfLike = (TextView) activity_board_reading.findViewById(R.id.TheNumberOfLike);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String content = extras.getString("content");
        String tag = extras.getString("tag");
        TitleOfWriting.setText(title);
        ContentOfWriting.setText(content);
        TagOfWriting.setText(tag);


        System.out.println("여기는 BoardReadingListViewActivity 들어온 거!! count_like는 " + count_like);
        System.out.println("click_like는 " + click_like);

        bLikeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;
                }
                count_like--;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;
                }
                count_like++;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);

            }
        });



    }
/*
    private void setFooter(){
        //Footer도 마찬가지로 앞에 footer.를 붙여줄것!
        comment_edit = (EditText)comment_write.findViewById(R.id.comment_edit);
        Button bCommentPost = (Button)comment_write.findViewById(R.id.bCommentPost);

        //implements를 맨 위에 선언해줬기 때문에, setOnClickListener를 여기서 설정할 수 있음
        bCommentPost.setOnClickListener(this);

    }

    private void setList(){
//        ca = new Comment_Adapter(getApplicationContext(), BoardReadingListViewActivity.this, BoardReadingListViewActivity.this, mCommentData);
//        comment_list.setAdapter(ca);
        comment_list.setSelection(mCommentData.size()-1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0,0);
    }

    @Override
    public void onClick(View v) {
        //Click 되었을때 Id값으로 클릭처리를 할 수 있습니다.
        //아까 setFooter에서 bCommentPost에 setOnClickListener를 달아 주었기 때문에 onClick이 사용 가능합니다.
        switch(v.getId()){
            case R.id.bCommentPost:
                String temp = comment_edit.getText().toString();
                if(temp.equals("")){
                    Toast.makeText(BoardReadingListViewActivity.this, "빈칸이 있습니다.", 0).show();
                }else{
                    //EditText의 빈칸이 없을 경우 등록!
                    CommentData cd = new CommentData("닉네임","2016-11-11",temp);
                    mCommentData.add(cd);
                    resetAdapter();
                    comment_edit.setText("");
                }
                break;
        }
    }
    public void resetAdapter(){
        ca.notifyDataSetChanged();
        //Adapter의 데이터 값이 변화가 있을 때 사용하는 함수.
    }

    public void deleteArr(int p){
        //Adapter에서 이 함수를 지울 때 호출합니다. 지우고자 하는 댓글의 id 값을 넘겨주면 mCommemtData 의 데이터를 삭제!
        mCommentData.remove(p);

        //마찬가지로 변화가 있었기 때문에 Adapter 초기화(?)
        ca.notifyDataSetChanged();
    }

    */
}