package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ImageView img_Logo;
    private EditText etEmail;
    private Button bSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bSendEmail = (Button) findViewById(R.id.bSendEmail);

        bSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(etEmail.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "인증할 이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
//                Intent intent = new Intent(getApplicationContext(), EmailActivity.class);
//                // 여기서 입력한 메일주소로 인증 메일 전송 + 다음 화면으로 전달
//                String email = etEmail.getText().toString();
//                intent.putExtra("email",email);
//                startActivityForResult(intent, 1000);

                //게시판 만들기 편하게 메일 입력에서 바로 게시판 뛰어넘게 해둠 / 나중에 경로 다시 수정
                Intent intent = new Intent(getApplicationContext(), BoardWritingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

    }
}
