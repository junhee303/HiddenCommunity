package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
    private ViewPager viewPager;

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
//
//        ListView listview_free = new ListView(mContext);
//        ListView listview_major1 = new ListView(mContext);
//        ListView listview_major2 = new ListView(mContext);
//        ListView listview_major3 = new ListView(mContext);
//
//        Vector<View> pages = new Vector<View>();
//
//        // 사용자가 가입 시 선택했던 전공의 게시판 생성
//        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
//        String Major1 = test.getString("UserMajor1", "");
//        String Major2 = test.getString("UserMajor2", "");
//        String Major3 = test.getString("UserMajor3", "");
//
//        pages.add(listview_free);
//        pages.add(listview_major1);
//        if(Major2 != "") {
//            pages.add(listview_major2);
//        }
//        if(Major3 != "") {
//            pages.add(listview_major3);
//        }
//
//        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
//        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(mContext,pages);
//        vp.setAdapter(adapter);
//
//        board_data.add(new BoardData("건축·토목", "○○기업 인턴 정보 공유하려고 합니다", "carsilverstar", "2016-11-13", "이번에 ○○기업에서 인턴 모집하네요~ \n 링크 첨부할테니 참고하세요! \n\nhttp://blog.swcode.net/entry/Action-Bar%EC%97%90-%EB%B2%84%ED%8A%BC-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0-1", "#인턴 #정보", 13, 2, 4)); //서버에서 받아와야함
//        board_data.add(new BoardData("건축·토목", "안드로이드 스튜디오 해보신 분 있으신가요??", "재주훈", "2016-11-12", "이번에 전공 수업에서 앱을 만드는 프로젝트가 있는데, 안드로이드를 처음 써봐서 모르겠는게 너무 많네요ㅠㅠ\n도움 주실 수 있는 분 댓글이나 채팅 부탁드립니다!!", "#안드로이드 #코딩", 3,4,2));
//        board_data.add(new BoardData("건축·토목", "안녕하세요~", "최우영", "2016-11-13", "게시판에 처음 글쓰네요\n앞으로 자주 소통하러 오겠습니다", "#인사", 2,0,1));
//
//
//        BoardListAdapter adapter_boardlist = new BoardListAdapter(mContext, 0, board_data);
//
//        listview_free.setAdapter(adapter_boardlist);
//        listview_major1.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,new String[]{"A2","B2","C2","D2"}));
//        listview_major2.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,new String[]{"A3","B3","C3","D3"}));
//        listview_major3.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,new String[]{"A4","B4","C4","D4"}));
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(vp);

//        tabLayout.addTab(tabLayout.newTab().setText("자유"));
//        tabLayout.addTab(tabLayout.newTab().setText("전공1"));
//==============================

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        //사용자한테 받아올 전공 데이터
//        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
//
//        String Major1 = test.getString("UserMajor1", "");
//        String Major2 = test.getString("UserMajor2", "");
//        String Major3 = test.getString("UserMajor3", "");
//
//        adapter.addFragment( new MyFragment(), "자유");
//        adapter.addFragment(new MyFragment(), Major1);
//        if(Major2 != "") {
//            adapter.addFragment(new MyFragment(), Major2);
//        }
//        if(Major3 != "") {
//            adapter.addFragment(new MyFragment(), Major3);
//        }

        adapter.addFragment(new MyFragment(), "자유");
        adapter.addFragment(new MyFragment(), "전공1");
        adapter.addFragment(new MyFragment(), "전공2");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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

    }

    private class BoardListAdapter extends ArrayAdapter<BoardData> {

        private ArrayList<BoardData> mBoardData;

        public BoardListAdapter(Context context, int resource, ArrayList<BoardData> boardData) {
            super(context, resource, boardData);

            mBoardData = boardData;
        }

        // BoardData에서 데이터 읽어와서 게시글 목록 작성
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_board, null, true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.Title);
            TextView txtAuthor = (TextView) rowView.findViewById(R.id.Author);
            TextView txtDate = (TextView) rowView.findViewById(R.id.Date);
            TextView txtBody = (TextView) rowView.findViewById(R.id.Body);
            TextView txtTag = (TextView) rowView.findViewById(R.id.Tag);

            TextView txtHit = (TextView) rowView.findViewById(R.id.count_hit);
            TextView txtLike = (TextView) rowView.findViewById(R.id.count_like);
            TextView txtComment = (TextView) rowView.findViewById(R.id.count_comment);

            txtTitle.setText(mBoardData.get(position).getTitle());
            txtAuthor.setText(mBoardData.get(position).getAuthor());
            txtDate.setText(mBoardData.get(position).getDate());
            txtBody.setText(mBoardData.get(position).getBody());
            //txtTag.setText(mBoardData.get(position).getTag());

//            txtHit.setText(mBoardData.get(position).getHit());
//            txtLike.setText(mBoardData.get(position).getLike());
//            txtComment.setText(mBoardData.get(position).getComment());
            // JSON에서 아직 못 받아옴

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }

    // 액션 바에 아이콘 추가 ==> 하단으로 내려야함..
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar bottom_toolbar;

        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    //하단 아이콘 클릭 시 액션
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent1 = new Intent(getApplicationContext(), SwipeBoardActivity.class);
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