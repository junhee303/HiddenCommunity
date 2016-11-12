package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import data.BoardData;

public class BoardWritingActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText Title;
    private EditText Content;
    private EditText Tag;
    private Button bPost;

    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writing);

        selectBoardOnSpinner();
        write_Board();
    }

//    public void selectBoardOnSpinner() {
//
//        spinner = (Spinner) findViewById(R.id.spinner);
//        List<String> list = new ArrayList<String>();
//        list.add("자유 게시판");
//        list.add("전공1 게시판");
//        list.add("전공2 게시판");
//        list.add("전공3 게시판");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//    }

    public void selectBoardOnSpinner(){
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void write_Board() {
        spinner = (Spinner) findViewById(R.id.spinner);
        Title = (EditText) findViewById(R.id.Title);
        Content = (EditText) findViewById(R.id.Content);
        Tag = (EditText) findViewById(R.id.Tag);
        bPost = (Button) findViewById(R.id.bPost);

        bPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                if(board_menu.getText().toString().length() == 0) {
//                    Toast.makeText(BoardWritingActivity.this, "게시판을 선택하세요", Toast.LENGTH_SHORT).show();
//                    board_menu.requestFocus();
//                    return;
//                }


                if (Title.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "제목 입력하세요", Toast.LENGTH_SHORT).show();
                    Title.requestFocus();
                    return;
                }
                if (Content.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    Content.requestFocus();
                    return;
                }

//                Toast.makeText(BoardWritingActivity.this,String.valueOf(spinner.getSelectedItem())+" 선택",
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
                // 여기서 입력한 메일주소로 인증 메일 전송 + 다음 화면으로 전달☆☆
                String board = spinner.getSelectedItem().toString();
                String title = Title.getText().toString();
                String content = Content.getText().toString();
                String tag = Tag.getText().toString();
                intent.putExtra("board",board);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("tag", tag);
                startActivityForResult(intent, 1000);


                // 게시글을 BoardData에 저장?? -> 안됨 ㅠ 클래스 달라서 안 넘어가나
                board_data.add(new BoardData(Title.getText().toString(), "닉네임", "날짜",  Content.getText().toString()));

            }
        });
    }
}
