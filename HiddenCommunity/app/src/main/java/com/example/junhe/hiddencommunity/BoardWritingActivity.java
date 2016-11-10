package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BoardWritingActivity extends AppCompatActivity {

    private Button board_menu;
    private EditText Title;
    private EditText Content;
    private EditText Tag;
    private Button bPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writing);

        board_menu = (Button) findViewById(R.id.board_menu);
        Title = (EditText) findViewById(R.id.Title);
        Content = (EditText) findViewById(R.id.Content);
        Tag = (EditText) findViewById(R.id.Tag);
        bPost = (Button) findViewById(R.id.bPost);

        bPost.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

//                if(board_menu.getText().toString().length() == 0) {
//                    Toast.makeText(BoardWritingActivity.this, "게시판을 선택하세요", Toast.LENGTH_SHORT).show();
//                    board_menu.requestFocus();
//                    return;
//                }
                if(Title.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "제목 입력하세요", Toast.LENGTH_SHORT).show();
                    Title.requestFocus();
                    return;
                }
                if(Content.getText().toString().length() == 0) {
                    Toast.makeText(BoardWritingActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    Content.requestFocus();
                    return;
                }
                System.out.println("여기는 BoardWritingActivity의 bPost 클릭 후 intent 전");
                Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
                // 여기서 입력한 메일주소로 인증 메일 전송 + 다음 화면으로 전달☆☆
                String title = Title.getText().toString();
                String content = Content.getText().toString();
                String tag = Tag.getText().toString();
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("tag",tag);
                startActivityForResult(intent, 1000);
                System.out.println("여기는 BoardWritingActivity의 bPost 클릭 후 intent 후");

            }
            });
    }
}
