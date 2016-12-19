package com.example.junhe.hiddencommunity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by junhe on 2016-11-20.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView loadingIcon = (ImageView) findViewById(R.id.loadingIcon);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        loadingIcon.setAnimation(animation);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000); // 2초 후에 gd handler 실행
        System.out.println("spalsh 2초간 실행");
    }

    private  class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }
}
