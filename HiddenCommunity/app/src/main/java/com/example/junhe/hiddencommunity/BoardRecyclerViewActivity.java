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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BoardRecyclerViewActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();
    private Spinner BoardRangeSpinner;
    private Button bHome;
    private Button bChat;
    private Button bSearch;
    private Button bNotice;
    private Button bWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recycler_view);

        BoardRangeSpinner = (Spinner) findViewById(R.id.board_range_spinner);
        bHome = (Button) findViewById(R.id.action_home);
        bChat = (Button) findViewById(R.id.action_chat);
        bSearch = (Button) findViewById(R.id.action_search);
        bNotice = (Button) findViewById(R.id.action_notice);
        bWrite = (Button) findViewById(R.id.action_write);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String Major1 = test.getString("UserMajor1", "");
        String Major2 = test.getString("UserMajor2", "");
        String Major3 = test.getString("UserMajor3", "");

        ArrayList<String> tabTitles = new ArrayList<>();


        tabTitles.add("자유");
        tabTitles.add(Major1);
        if (Major2 != "") {
            tabTitles.add(Major2);
        }
        if (Major3 != "") {
            tabTitles.add(Major3);
        }

        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), BoardRecyclerViewActivity.this, tabTitles);

        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        selectRangeSpinner(); // 게시판 정렬 선택
        pushBottomIcon(); // 하단 아이콘 클릭 시 액션
    }

    // 게시판 정렬 선택
    public void selectRangeSpinner() {

        BoardRangeSpinner = (Spinner) findViewById(R.id.board_range_spinner);

        list.add("최신순");
        list.add("조회순");
        list.add("좋아요순");
        list.add("댓글순");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BoardRangeSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                    return new BoardBlankFragment();
                case 1:
                    return new BoardBlankFragment();
                case 2:
                    return new BoardBlankFragment();
                case 3:
                    return new BoardBlankFragment();
            }

            return null;
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
        bChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '채팅 아이콘' 클릭 시 채팅 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "채팅 메뉴로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '검색 아이콘' 클릭 시 게시글 검색 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "검색 메뉴로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        bNotice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '알림 아이콘' 클릭 시 게시글 알림 메뉴로 이동
                Toast.makeText(BoardRecyclerViewActivity.this, "알림 메뉴로 이동", Toast.LENGTH_SHORT).show();
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
