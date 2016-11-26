package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.BoardData;

public class BoardWritingActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText Title;
    private EditText Content;
    private EditText Tag;
    private Button bPost;

    String url, result;

    private List<String> list = new ArrayList<String>();

    class PostWritingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = HTTPInstance.Instance().Post(url);
                onResponseHttp(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    PostWritingTask postWritingTask;

    private void onResponseHttp(String s) {
        System.out.println(s);
        if (s == null) {
//            mProgressDialog = ProgressDialog.show(.this,"",
//                    "잠시만 기다려 주세요.",true);
            System.out.println("작성한 글이 등록되지 않았습니다.");
            return;
        }
        Log.d("boardId", s);
        if (s != null) { // 작성 게시글 id 넘어옴
            System.out.println("작성한 글의 아이디는 :" + s);

            Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
            String boardId = s;
            intent.putExtra("boardId", boardId);
            startActivityForResult(intent, 1000);
        } else {

        }
    }

    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writing);

        selectBoardOnSpinner();
        write_Board();
    }

    public void selectBoardOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);


        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String major1 = test.getString("UserMajor1", "");
        String major2 = test.getString("UserMajor2", "");
        String major3 = test.getString("UserMajor3", "");

        list.add("자유");
        if (major1 != "") {
            list.add(major1);
        }
        if (major2 != "") {
            list.add(major2);
        }
        if (major3 != "") {
            list.add(major3);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

//    public void selectBoardOnSpinner(){
//        spinner = (Spinner) findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//    }

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

                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);

                String major = spinner.getSelectedItem().toString();
                String title = Title.getText().toString();
                String author = test.getString("UserNickname", null);
                String body = Content.getText().toString();
                String tag = Tag.getText().toString();

                System.out.println(major+" / "+title+" / "+author+" / "+body+" / "+tag);

                // 서버로 게시글 전달
                try {
                    url = "http://52.78.207.133:3000/boards/new?";
                    url += "major=" + URLEncoder.encode(major, "utf-8");
                    url += "&title=" + URLEncoder.encode(title, "utf-8");
                    url += "&author=" + URLEncoder.encode(author, "utf-8");
                    url += "&body=" + URLEncoder.encode(body, "utf-8");
                    url += "&tag=" + URLEncoder.encode(tag, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                postWritingTask = new PostWritingTask();
                postWritingTask.execute();


//                Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
//                // 여기서 입력한 메일주소로 인증 메일 전송 + 다음 화면으로 전달☆☆
//                String board = spinner.getSelectedItem().toString();
//                String title = Title.getText().toString();
//                String content = Content.getText().toString();
//                String tag = Tag.getText().toString();
//                intent.putExtra("board", board);
//                intent.putExtra("title", title);
//                intent.putExtra("content", content);
//                intent.putExtra("tag", tag);
//                startActivityForResult(intent, 1000);
            }
        });
    }
}
