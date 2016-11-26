package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;

import data.BoardData;


/**
 * Created by junhe on 2016-11-21.
 */

public class SwipeBoardActivity extends AppCompatActivity {
    private  Context mContext;
    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();

    String url, result;

    class NoticeBoardTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

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

    NoticeBoardTask noticeBoardTask;

    private void onResponseHttp(String s) {
//        if (s == null) {
////            mProgressDialog = ProgressDialog.show(.this,"",
////                    "잠시만 기다려 주세요.",true);
//            System.out.println("회원 정보를 정확히 입력해주세요");
//            return;
//        }
//        Log.d("RESPONSE", s);
//        if (s.compareTo("ok") == 0) {
//            System.out.println("가입이 완료되었습니다.");
//            Intent intent = new Intent(getApplicationContext(), NoticeBoardActivity.class);
//            startActivityForResult(intent, 1000);
//        } else {
//
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_swipe_board);

//        Vector<View> pages = new Vector<View>();
//
//        ListView listview1 = new ListView(mContext);
//        ListView listview2 = new ListView(mContext);
//
//        pages.add(listview1);
//        pages.add(listview2);

//        try {
//            JSONObject jsonObject = new JSONObject(result.toString());
//            String resultUserNickname = jsonObject.getString("nickname");
//            String resultUserMajor1 = jsonObject.getString("major1");
//            String resultUserMajor2 = jsonObject.getString("major2");
//            String resultUserMajor3= jsonObject.getString("major3");
//            System.out.println("서버에서 받아온 JSON 파싱!!");
//            System.out.println(resultUserNickname + "\n" + resultUserMajor1+ "\n" + resultUserMajor2+ "\n" + resultUserMajor3);
//
//            if(resultUserMajor2 != null){
//                ListView listview3 = new ListView(mContext);
//                pages.add(listview3);
//            }
//
//            if(resultUserMajor3 != null){
//                ListView listview4 = new ListView(mContext);
//                pages.add(listview4);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//
//        board_data.add(new BoardData("○○기업 인턴 정보 공유하려고 합니다", "carsilverstar", "2016-11-13", "이번에 ○○기업에서 인턴 모집하네요~ \n 링크 첨부할테니 참고하세요! \n\nhttp://blog.swcode.net/entry/Action-Bar%EC%97%90-%EB%B2%84%ED%8A%BC-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0-1")); //서버에서 받아와야함
//        board_data.add(new BoardData("안드로이드 스튜디오 해보신 분 있으신가요??", "재주훈", "2016-11-12", "이번에 전공 수업에서 앱을 만드는 프로젝트가 있는데, 안드로이드를 처음 써봐서 모르겠는게 너무 많네요ㅠㅠ\n도움 주실 수 있는 분 댓글이나 채팅 부탁드립니다!! "));
//        board_data.add(new BoardData("안녕하세요~", "최우영", "2016-11-13", "게시판에 처음 글쓰네요\n앞으로 자주 소통하러 오겠습니다"));
//
//       // listview1.setAdapter(new ArrayAdapter<BoardData>(mContext, android.R.layout.list_content,board_data));
//        BoardListAdapter adapter_boardlist = new BoardListAdapter(mContext, 0, board_data);
//        // ListView에 각각의 게시글을 제어하는 Adapter를 설정
//        listview1.setAdapter(adapter_boardlist);

        // Initializing ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment( new MyFragment(), "자유 게시판");
        adapter.addFragment(new MyFragment(), "전공1 게시판");
        adapter.addFragment(new MyFragment(), "전공2 게시판");
        adapter.addFragment(new MyFragment(), "전공3 게시판");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar bottom_toolbar;

        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent1 = new Intent(getApplicationContext(), NoticeBoardActivity.class);
                startActivityForResult(intent1, 1000);
                return true;
//            case R.id.action_chat:
//                Intent intent2 = new Intent(getApplicationContext(), ChattingActivity.class);
//                startActivityForResult(intent2, 1000);
//                return true;
//            case R.id.action_search:
//                Intent intent3 = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivityForResult(intent3, 1000);
//                return true;
//            case R.id.action_notice:
//                Intent intent4 = new Intent(getApplicationContext(), NoticeActivity.class);
//                startActivityForResult(intent4, 1000);
//                return true;
            case R.id.action_write:
                Intent intent5 = new Intent(getApplicationContext(), BoardWritingActivity.class);
                startActivityForResult(intent5, 1000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}