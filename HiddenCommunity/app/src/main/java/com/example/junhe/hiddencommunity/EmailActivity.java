package com.example.junhe.hiddencommunity;

import android.app.ProgressDialog;
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
  //  private ProgressDialog mProgressDialog;

    TextView input_email;

    String url,result;

    private ProgressDialog mProgressDialog;
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
//            mProgressDialog = ProgressDialog.show(EmailActivity.this,"",
//                    "잠시만 기다려 주세요.",true);
            System.out.println("다시 인증해 주세요");
//            Dialog progressDialog = new Dialog(context);
//            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            progressDialog.setContentView(dialogView);
//            progressDialog.show();
            return;
        }
        Log.d("RESPONSE", s);
        if(s.compareTo("ok")==0){
            //Toast.makeText(EmailActivity.this, "인증이 완료되었습니다", Toast.LENGTH_SHORT).show();
//            return;
            System.out.println("인증이 완료되었습니다.");
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            String email = input_email.getText().toString();
            intent.putExtra("email",email);

            startActivityForResult(intent, 1000);
        } else {

        }
        //Toast.makeText(EmailActivity.this, "다시 인증해 주세요", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        confirmEmail();
    }

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
                url = "http://52.78.207.133:3000/send/user/validate?email="+input_email.getText();
                validateTask = new ValidateTask();
                validateTask.execute();
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


