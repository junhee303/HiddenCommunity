package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import data.BoardData;


/**
 * Created by junhe on 2016-11-21.
 */

public class SwipeBoardActivity extends AppCompatActivity {
    Context mContext = this;
    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_board);

        // Initializing ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyFragment(), "자유 게시판");
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

    private class NoticeBoardAdapter extends ArrayAdapter<BoardData> {

        private ArrayList<BoardData> mBoardData;

        public NoticeBoardAdapter(Context context, int resource, ArrayList<BoardData> boardData) {
            super(context, resource, boardData);

            mBoardData = boardData;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_board, null, true);

            TextView txtinput_title = (TextView) rowView.findViewById(R.id.Title);
            TextView txtNickname = (TextView) rowView.findViewById(R.id.Nickname);
            TextView txtDate = (TextView) rowView.findViewById(R.id.Date);
            TextView txtinput_content = (TextView) rowView.findViewById(R.id.Content);

            txtinput_title.setText((CharSequence) mBoardData.get(position).getTitle());
            txtNickname.setText((CharSequence) mBoardData.get(position).getNickname());
            txtDate.setText((CharSequence) mBoardData.get(position).getDate());
            txtinput_content.setText((CharSequence) mBoardData.get(position).getContent());

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }

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