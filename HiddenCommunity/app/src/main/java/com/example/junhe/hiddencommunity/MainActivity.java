package com.example.junhe.hiddencommunity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by junhe on 2016-11-20.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String email = test.getString("UserEmail", null);

        // 사용자의 이메일 세션 존재 시 자동 로그인
        if(email != null) {
            Intent intent = new Intent(getApplicationContext(), BoardRecyclerViewActivity.class);
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
