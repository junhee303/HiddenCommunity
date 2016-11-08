package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class EmailActivity extends AppCompatActivity {

    private ImageView img_Number;
    private ImageView img_Email;
    private Button bFinish;
    TextView input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        img_Email = (ImageView) findViewById(R.id.img_Email);
        input_email = (TextView) findViewById(R.id.input_email);
        bFinish = (Button) findViewById(R.id.bFinish);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        input_email.setText(email);

        bFinish.setOnClickListener(new View.OnClickListener() {
            // 대학교 메일을 통한 인증 완료시에만 인증완료 가능하게 하기!!☆☆
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 동시에 사용 가능
                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)를 준다.
                startActivityForResult(intent, 1000);
            }
        });
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(EmailActivity.this, "이메일 인증을 완료했습니다!", Toast.LENGTH_SHORT).show();
            etUserEmail.setText(data.getStringExtra("email"));
        }
    }*/
}


