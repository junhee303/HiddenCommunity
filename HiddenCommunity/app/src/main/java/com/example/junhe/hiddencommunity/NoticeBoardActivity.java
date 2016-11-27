package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

import data.BoardData;


public class NoticeBoardActivity extends AppCompatActivity {
    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();
    Context mContext = this;

    private Toolbar bottom_toolbar;

    private ViewPager pager;

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        MenuInflater inflater = getMenuInflater();
//        //inflater.inflate(R.menu.notice_board, menu);
//        inflater.inflate(R.menu.bottom_navigation, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        //setTitle(null);

        board_data.add(new BoardData("건축·토목", "○○기업 인턴 정보 공유하려고 합니다", "carsilverstar", "2016-11-13", "이번에 ○○기업에서 인턴 모집하네요~ \n 링크 첨부할테니 참고하세요! \n\nhttp://blog.swcode.net/entry/Action-Bar%EC%97%90-%EB%B2%84%ED%8A%BC-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0-1", "#인턴 #정보", 13, 2, 4)); //서버에서 받아와야함
        board_data.add(new BoardData("건축·토목", "안드로이드 스튜디오 해보신 분 있으신가요??", "재주훈", "2016-11-12", "이번에 전공 수업에서 앱을 만드는 프로젝트가 있는데, 안드로이드를 처음 써봐서 모르겠는게 너무 많네요ㅠㅠ\n도움 주실 수 있는 분 댓글이나 채팅 부탁드립니다!!", "#안드로이드 #코딩", 3,4,2));
        board_data.add(new BoardData("건축·토목", "안녕하세요~", "최우영", "2016-11-13", "게시판에 처음 글쓰네요\n앞으로 자주 소통하러 오겠습니다", "#인사", 2,0,1));

        // 게시글 ListView 가져오기
        ListView board_list = (ListView) findViewById(R.id.board_list);
        BoardListAdapter adapter_boardlist = new BoardListAdapter(mContext, 0, board_data);
        // ListView에 각각의 게시글을 제어하는 Adapter를 설정
        board_list.setAdapter(adapter_boardlist);


        // 게시글 클릭시 이벤트 리스너 등록
        board_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView board_list = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                // 전공 선택시 RegisterActivity의 etMajor에 전달되어야 함☆☆☆☆☆
            }
        });

    }

    private class BoardListAdapter extends ArrayAdapter<BoardData> {

        private ArrayList<BoardData> mBoardData;

        public BoardListAdapter(Context context, int resource, ArrayList<BoardData> boardData) {
            super(context, resource, boardData);

            mBoardData = boardData;
        }


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
            txtTag.setText(mBoardData.get(position).getTag());

            txtHit.setText(mBoardData.get(position).getHit());
            txtLike.setText(mBoardData.get(position).getLike());
            txtComment.setText(mBoardData.get(position).getComment());

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }

//    //onClick속성이 지정된 View를 클릭했을때 자동으로 호출되는 메소드
//    public void mOnClick(View v){
//
//        int position;
//
//        switch( v.getId() ){
//            case R.id.btn_previous://이전버튼 클릭
//
//                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
//
//                //현재 위치(position)에서 -1 을 해서 이전 position으로 변경
//                //이전 Item으로 현재의 아이템 변경 설정(가장 처음이면 더이상 이동하지 않음)
//                //첫번째 파라미터: 설정할 현재 위치
//                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
//                pager.setCurrentItem(position-1,true);
//
//                break;
//
//            case R.id.btn_next://다음버튼 클릭
//
//                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
//
//                //현재 위치(position)에서 +1 을 해서 다음 position으로 변경
//                //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)
//                //첫번째 파라미터: 설정할 현재 위치
//                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
//                pager.setCurrentItem(position+1,true);
//
//                break;
//        }
//
//    }
}


