package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.junhe.hiddencommunity.network.DataRequest;
import com.example.junhe.hiddencommunity.network.VolleySingleton;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ImageView img_Logo;
    private EditText etEmail;
    private Button bSendEmail;

    String testurl, result;

    class TestTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = HTTPInstance.Instance().Post(testurl);
                onResponseHttp(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    TestTask _test;

    void onResponseHttp(String s) {
        if (s == null) {
            return;
        }
        Log.d("RESPONSE", s);
        if (s.compareTo("ok") == 0) {
            Intent intent = new Intent(getApplicationContext(), EmailActivity.class);
            String email = etEmail.getText().toString();
            intent.putExtra("email", email);
            startActivityForResult(intent, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        DataRequest.sendJsonDataRequest(requestQueue, getRequestUrl());

        inputEmail(); // 인증 받을 메일 입력
    }

    public static final String getRequestUrl() {
        String url = "http://ip.jsontest.com/";
        Log.d("TAG", url);
        return url;
    }

    // 인증 받을 메일 입력
    public void inputEmail() {

        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bSendEmail = (Button) findViewById(R.id.bSendEmail);

        bSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "인증할 이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                // 입력한 메일주소로 인증 메일 전송 + 다음 화면으로 전달
                String email = etEmail.getText().toString();

                if (email.matches(".*.ac.kr.*")) {
                    testurl = "http://52.78.207.133:3000/emails/send/" + email.toString();
                    _test = new TestTask();
                    _test.execute();

                    Toast.makeText(LoginActivity.this, "메일을 전송 중 입니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "대학 계정 메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etEmail.requestFocus();
                    return;
                }
            }
        });
    }
}


