package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BoardRecyclerViewActivity extends AppCompatActivity {

    private Button bHome;
    private Button bMessage;
    private Button bSearch;
    private Button bNotice;
    private Button bWrite;

    ArrayList<String> tabTitles;
    int board_position = 0;

    String Major1;
    String Major2;
    String Major3;

    int range_position;

    PagerAdapter pagerAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recycler_view);

        bHome = (Button) findViewById(R.id.action_home);
        bMessage = (Button) findViewById(R.id.action_message);
        bSearch = (Button) findViewById(R.id.action_search);
        bNotice = (Button) findViewById(R.id.action_notice);
        bWrite = (Button) findViewById(R.id.action_write);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        Major1 = test.getString("UserMajor1", "");
        Major2 = test.getString("UserMajor2", "");
        Major3 = test.getString("UserMajor3", "");

        tabTitles = new ArrayList<>();

        // 사용자의 전공 선택 개수에 따라 tab 추가
        tabTitles.add("자유");
        tabTitles.add(Major1);
        if (Major2 != "") {
            tabTitles.add(Major2);
        }
        if (Major3 != "") {
            tabTitles.add(Major3);
        }

        pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), BoardRecyclerViewActivity.this, tabTitles);

        mViewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        pushBottomIcon(); // 하단 아이콘 클릭 시 액션
    }

    // ViewPager Adapter
    class PagerAdapter extends FragmentPagerAdapter {

        ArrayList<String> tabTitles = new ArrayList<>();
        Context context;


        public PagerAdapter(FragmentManager fm, Context context, ArrayList<String> tabTitles) {
            super(fm);
            this.context = context;
            this.tabTitles = tabTitles;
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    BoardBlankFragment fragment = new BoardBlankFragment();
                    Bundle args = new Bundle();
                    args.putInt("index", 0);
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    fragment = new BoardBlankFragment();
                    args = new Bundle();
                    args.putInt("index", 1);
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                    if (Major2 != "") {
                        fragment = new BoardBlankFragment();
                        args = new Bundle();
                        args.putInt("index", 2);
                        fragment.setArguments(args);
                        return fragment;
                    }
                case 3:
                    if (Major3 != "") {
                        fragment = new BoardBlankFragment();
                        args = new Bundle();
                        args.putInt("index", 3);
                        fragment.setArguments(args);
                        return fragment;
                    }
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position);
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(BoardRecyclerViewActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }

    }

    // 하단 아이콘 클릭 시 액션
    public void pushBottomIcon() {
        bHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '홈 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "자유 게시판으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoardRecyclerViewActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '말풍선 아이콘' 클릭 시 쪽지 보내기 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "쪽지 보내기 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoardRecyclerViewActivity.this, MessageListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '검색 아이콘' 클릭 시 게시글 검색 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "검색 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoardRecyclerViewActivity.this, BoardSearchActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bNotice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '알림 아이콘' 클릭 시 게시글 알림 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "알림 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoardRecyclerViewActivity.this, NoticeListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '글쓰기 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "게시물 작성하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoardRecyclerViewActivity.this, BoardWritingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

}
