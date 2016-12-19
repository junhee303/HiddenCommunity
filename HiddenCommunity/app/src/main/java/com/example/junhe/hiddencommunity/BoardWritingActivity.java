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

    private List<String> list = new ArrayList<>();
    private Spinner Category;
    private EditText Title;
    private EditText Body;
    private EditText Tag;
    private Button bPost;

    String url, result;

    class PostWritingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = HTTPInstance.Instance().Post(url);
                Log.d("result: ", result);
                onResponseHttp(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    PostWritingTask postWritingTask;

    private void onResponseHttp(String s) {
        Log.d("result: ", s);
        if (s == null) {
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

        selectBoardOnSpinner(); // 게시물 작성할 게시판 선택
        write_Board(); // 게시글 작성 및 서버 전송
    }

    // 게시물 작성할 게시판 선택
    public void selectBoardOnSpinner() {

        Category = (Spinner) findViewById(R.id.Category);

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
        Category.setAdapter(dataAdapter);
    }

    // 게시글 작성 및 서버 전송
    public void write_Board() {
        Category = (Spinner) findViewById(R.id.Category);
        Title = (EditText) findViewById(R.id.Title);
        Body = (EditText) findViewById(R.id.Body);
        Tag = (EditText) findViewById(R.id.Tag);
        bPost = (Button) findViewById(R.id.bPost);

        bPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Title.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "제목 입력하세요", Toast.LENGTH_SHORT).show();
                    Title.requestFocus();
                    return;
                }
                if (Body.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    Body.requestFocus();
                    return;
                }

                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);

                String category = Category.getSelectedItem().toString();
                String title = Title.getText().toString();
                String author = test.getString("UserNickname", null);
                String body = Body.getText().toString();
                String tag = Tag.getText().toString();
                System.out.println(category + " / " + title + " / " + author + " / " + body + " / " + tag);

                // 서버로 게시글 전달
                try {
                    url = "http://52.78.207.133:3000/boards/new?";
                    url += "category=" + URLEncoder.encode(category, "utf-8");
                    url += "&title=" + URLEncoder.encode(title, "utf-8");
                    url += "&author=" + URLEncoder.encode(author, "utf-8");
                    url += "&body=" + URLEncoder.encode(body, "utf-8");
                    url += "&tag=" + URLEncoder.encode(tag, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                postWritingTask = new PostWritingTask();
                postWritingTask.execute();

            }

        });
    }
}
