package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BoardUpdateActivity extends AppCompatActivity {

    private TextView Category;
    private EditText Title;
    private EditText Body;
    private EditText Tag;
    private Button bUpdate;

    String url, result;

    private List<String> list = new ArrayList<String>();

    class UpdateWritingTask extends AsyncTask<String, Void, String> {
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

    UpdateWritingTask updateWritingTask;

    private void onResponseHttp(String s) {
        Log.d("result: ", s);
        if (s == null) {
            System.out.println("글이 수정되지 않았습니다.");
            return;
        }
        Log.d("boardId", s);
        if (s != null) { // 작성 게시글 id 넘어옴
            System.out.println("수정한 글의 아이디는 :" + s);

            Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
            String boardId = s;
            intent.putExtra("boardId", boardId);
            startActivityForResult(intent, 1000);
        } else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);

        update_Board();
    }

    // 게시글 수정하기
    public void update_Board() {
        Category = (TextView) findViewById(R.id.Category);
        Title = (EditText) findViewById(R.id.Title);
        Body = (EditText) findViewById(R.id.Body);
        Tag = (EditText) findViewById(R.id.Tag);
        bUpdate = (Button) findViewById(R.id.bUpdate);

        final Bundle extras = getIntent().getExtras();
        String txtCategory = extras.getString("txtCategory");
        String txtTitle = extras.getString("txtTitle");
        String txtBody = extras.getString("txtBody");
        String txtTag = extras.getString("txtTag");
        Category.setText(txtCategory);
        Title.setText(txtTitle);
        Body.setText(txtBody);
        Tag.setText(txtTag);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Title.getText().toString().length() == 0) {
                    Toast.makeText(BoardUpdateActivity.this, "제목 입력하세요", Toast.LENGTH_SHORT).show();
                    Title.requestFocus();
                    return;
                }
                if (Body.getText().toString().length() == 0) {
                    Toast.makeText(BoardUpdateActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    Body.requestFocus();
                    return;
                }

                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);

                String category = Category.getText().toString();
                String title = Title.getText().toString();
                String author = test.getString("UserNickname", null);
                String body = Body.getText().toString();
                String tag = Tag.getText().toString();
                System.out.println(category + " / " + title + " / " + author + " / " + body + " / " + tag);

                // 서버로 수정된 게시글 전달
                try {

                    String boardId = extras.getString("boardId");
                    url = "http://52.78.207.133:3000/boards/update/"+ boardId + "?";
                    url += "category=" + URLEncoder.encode(category, "utf-8");
                    url += "&title=" + URLEncoder.encode(title, "utf-8");
                    url += "&author=" + URLEncoder.encode(author, "utf-8");
                    url += "&body=" + URLEncoder.encode(body, "utf-8");
                    url += "&tag=" + URLEncoder.encode(tag, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                updateWritingTask = new UpdateWritingTask();
                updateWritingTask.execute();

            }

        });
    }
}