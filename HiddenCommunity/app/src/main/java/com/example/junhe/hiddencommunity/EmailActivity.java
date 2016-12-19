package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class EmailActivity extends AppCompatActivity {

    private ImageView img_Number;
    private ImageView img_Email;
    private Button bFinish;

    TextView input_email;
    String url,result;

    class ValidateTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {}
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

    ValidateTask validateTask;

    private void onResponseHttp(String s){
        if(s == null) {
            System.out.println("다시 인증해 주세요");
            return;
        }
        Log.d("RESPONSE", s);
        if(s.compareTo("ok")==0){
            System.out.println("인증이 완료되었습니다.");
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            String email = input_email.getText().toString();
            intent.putExtra("email",email);
            startActivityForResult(intent, 1000);
        } else {
            System.out.println("다시 인증해 주세요");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        confirmEmail(); // 이메일 인증
    }

    // 이메일 인증
    public void confirmEmail() {
        img_Email = (ImageView) findViewById(R.id.img_Email);
        input_email = (TextView) findViewById(R.id.input_email);
        bFinish = (Button) findViewById(R.id.bFinish);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        input_email.setText(email);

        bFinish.setOnClickListener(new View.OnClickListener() {
            // 대학교 메일을 통한 인증 완료시에만 인증완료 가능하게 하기!!☆☆
            public void onClick(View v) {
                url = "http://52.78.207.133:3000/emails/validate/"+input_email.getText();
                validateTask = new ValidateTask();
                validateTask.execute();
            }
        });
    }
}


