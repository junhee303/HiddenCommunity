package com.example.junhe.hiddencommunity;

/**
 * Created by junhe on 2016-11-22.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.BoardData;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyFragment extends Fragment {
    private ArrayList<BoardData> board_data = new ArrayList<BoardData>();
    private ListView board_list;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resId = R.layout.activity_notice_board;

        board_data.add(new BoardData("○○기업 인턴 정보 공유하려고 합니다", "carsilverstar", "2016-11-13", "이번에 ○○기업에서 인턴 모집하네요~ \n 링크 첨부할테니 참고하세요! \n\nhttp://blog.swcode.net/entry/Action-Bar%EC%97%90-%EB%B2%84%ED%8A%BC-%EC%B6%94%EA%B0%80%ED%95%98%EA%B8%B0-1")); //서버에서 받아와야함
        board_data.add(new BoardData("안드로이드 스튜디오 해보신 분 있으신가요??", "재주훈", "2016-11-12", "이번에 전공 수업에서 앱을 만드는 프로젝트가 있는데, 안드로이드를 처음 써봐서 모르겠는게 너무 많네요ㅠㅠ\n도움 주실 수 있는 분 댓글이나 채팅 부탁드립니다!! "));
        board_data.add(new BoardData("안녕하세요~", "최우영", "2016-11-13", "게시판에 처음 글쓰네요\n앞으로 자주 소통하러 오겠습니다"));

        // 게시글 ListView 가져오기
        board_list = (ListView) board_list.findViewById(R.id.board_list);
        BoardListAdapter adapter_boardlist = new BoardListAdapter(mContext, 0, board_data);
        // ListView에 각각의 게시글을 제어하는 Adapter를 설정
        board_list.setAdapter(adapter_boardlist);

        return inflater.inflate(resId, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
            TextView txtNickname = (TextView) rowView.findViewById(R.id.Nickname);
            TextView txtDate = (TextView) rowView.findViewById(R.id.Date);
            TextView txtContent = (TextView) rowView.findViewById(R.id.Content);

            TextView txtTag = (TextView) rowView.findViewById(R.id.Tag);
            TextView txtHit = (TextView) rowView.findViewById(R.id.count_hit);
            TextView txtLike = (TextView) rowView.findViewById(R.id.count_like);
            TextView txtComment = (TextView) rowView.findViewById(R.id.count_comment);

            txtTitle.setText(mBoardData.get(position).getTitle());
            txtNickname.setText(mBoardData.get(position).getNickname());
            txtDate.setText(mBoardData.get(position).getDate());
            txtContent.setText(mBoardData.get(position).getContent());

//            txtTag.setText(mBoardData.get(position).getTag());
//            txtHit.setText(mBoardData.get(position).getCountHit());
//            txtLike.setText(mBoardData.get(position).getCountLike());
//            txtComment.setText(mBoardData.get(position).getCountComment());

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }
}