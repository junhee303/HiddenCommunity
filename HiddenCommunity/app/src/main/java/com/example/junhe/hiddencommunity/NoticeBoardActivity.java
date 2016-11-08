package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.BoardData;

import static android.R.attr.data;


public class NoticeBoardActivity extends AppCompatActivity {

//    TextView input_title;
//    TextView input_content;

    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

//        input_title = (TextView) findViewById(R.id.Title);
        TextView Title = (TextView) findViewById(R.id.Title);
        TextView Nickname = (TextView) findViewById(R.id.Nickname);
        TextView Date = (TextView) findViewById(R.id.Date);
        TextView Content = (TextView) findViewById(R.id.Content);
//        input_content = (TextView) findViewById(R.id.Content);

        //BoardWritingActivity에서 제목,내용 받아오고, 사용자 정보에서 닉네임 받아오고, 날짜 받아오기
//        Bundle extras1 = getIntent().getExtras();
//        String title = extras1.getString("title");
//        input_title.setText(title);
//
//        Bundle extras2 = getIntent().getExtras();
//        String content = extras2.getString("content");
//        input_content.setText(content);

        // 게시글 추가
//        board_data.add(new BoardData(input_title, Nickname, Date,input_content));
        board_data.add(new BoardData(Title, Nickname, Date,Content));


        // ListView 가져오기
        ListView board_list = (ListView) findViewById(R.id.Boardlist);

        NoticeBoatdAdapter adapter = new NoticeBoatdAdapter(mContext, 0, board_data);
        // ListView에 각각의 전공표시를 제어하는 Adapter를 설정
        board_list.setAdapter(adapter);

        // 전공 클릭시 이벤트 리스너 등록
        board_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // ListView major_list = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                // 전공 선택시 RegisterActivity의 etMajor에 전달되어야 함☆☆☆☆☆

            }

        });

    }

    private class NoticeBoatdAdapter extends ArrayAdapter<BoardData> {

        private ArrayList<BoardData> mBoardData;

        public NoticeBoatdAdapter(Context context, int resource, ArrayList<BoardData> boardData) {
            super(context, resource, boardData);

            mBoardData = boardData;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView= inflater.inflate(R.layout.list_board, null, true);

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


}


