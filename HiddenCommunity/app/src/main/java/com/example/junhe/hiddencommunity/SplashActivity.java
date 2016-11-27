package com.example.junhe.hiddencommunity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by junhe on 2016-11-20.
 */

public class SplashActivity extends AppCompatActivity {

    String url, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("스플레쉬 액티비티의 온크리에이트");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0,3000); // 왜 3초대기안해?!


              //  게시판 만들기 편하게 메일 입력에서 바로 게시판 뛰어넘게 해둠 / 나중에 경로 다시 수정

//                Intent intent = new Intent(getApplicationContext(), SwipeBoardActivity.class);
//                startActivityForResult(intent, 1000);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);

        String email = test.getString("UserEmail", null);

        if(email != null) {
            Intent intent = new Intent(getApplicationContext(), SwipeBoardActivity.class);
            startActivityForResult(intent, 1000);
            System.out.println("사용자 세션 존재하므로 게시판 화면으로 이동");
            System.out.println(email);

        } else if (email == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 1000);
            System.out.println("사용자 세션 없으므로 로그인 화면으로 이동");
            System.out.println(email);
        }

    }

}
