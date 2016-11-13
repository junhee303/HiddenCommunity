package com.example.junhe.hiddencommunity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.CommentData;

public class BoardReadingActivity extends AppCompatActivity {

    private TextView select_board;
    private TextView TitleOfWriting;
    private TextView Writer;
    private TextView DateOfWriting;
    private TextView ContentOfWriting;
    private TextView TagOfWriting;
    private Button bLikeOn;
    private Button bLikeOff;
    private Button bAddComment;
    private TextView TheNumberOfLike;
    private boolean click_like = false;
    private int count_like = 0;
    private int count_comment = 0;

    final Context context = this;
    //private TextView postedComment;

    private ArrayList<CommentData> comment_data = new ArrayList<CommentData>();
    Context mContext = this;

    ListView comment_list;
    ScrollView scrollView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.board_reading_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_reading);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        select_board = (TextView) findViewById(R.id.select_board);
        TitleOfWriting = (TextView) findViewById(R.id.TitleOfWriting);
        Writer = (TextView) findViewById(R.id.Writer);
        DateOfWriting = (TextView) findViewById(R.id.DateOfWriting);
        ContentOfWriting = (TextView) findViewById(R.id.ContentOfWriting);
        TagOfWriting = (TextView) findViewById(R.id.TagOfWriting);
        bLikeOn = (Button) findViewById(R.id.bLikeOn);
        bLikeOff = (Button) findViewById(R.id.bLikeOff);
        bAddComment = (Button) findViewById(R.id.bAddComment);
        TheNumberOfLike = (TextView) findViewById(R.id.TheNumberOfLike);

        // BoardWritingActivity에서 전달받은 String -> 서버상에서 해결해야함
        Bundle extras = getIntent().getExtras();
        String board = extras.getString("board");
        String title = extras.getString("title");
        String content = extras.getString("content");
        String tag = extras.getString("tag");

        select_board.setText(board);
        TitleOfWriting.setText(title);
        ContentOfWriting.setText(content);
        TagOfWriting.setText(tag);

        pushLikeButton(); // 좋아요
        pushCommentButton(); // 댓글 쓰기

    }

    public void pushLikeButton() {
        bLikeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // 좋아요 클릭 시 까만 하트

                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;
                }
                count_like--;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // 좋아요 해제 시 하얀 하트

                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;
                }
                count_like++;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);
            }
        });
    }

    public void pushCommentButton() {
        bAddComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View commentPopupView = li.inflate(R.layout.comment_post_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(commentPopupView); // 팝업창으로 댓글 쓰기

                final EditText userInput = (EditText) commentPopupView.findViewById(R.id.editTextDialogComment);

                // 작성한 댓글 내용 입력
                alertDialogBuilder.setCancelable(false).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        if (userInput.getText().toString().length() == 0) {
                            Toast.makeText(BoardReadingActivity.this, "입력한 내용이 없습니다", Toast.LENGTH_SHORT).show();
                            userInput.requestFocus();

                        } else {
                            comment_data.add(new CommentData("닉네임", "날짜", userInput.getText().toString()));
                            // ListView 가져오기
                            comment_list = (ListView) findViewById(R.id.comment_list);
                            CommentListAdapter adapter = new CommentListAdapter(mContext, 0, comment_data);
                            // ListView에 각각의 전공표시를 제어하는 Adapter를 설정
                            comment_list.setAdapter(adapter);

                            setListViewHeightBasedOnChildren(comment_list); // 댓글 리스트뷰 높이만큼 다 보이게 세팅
                        }
                    }
                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    private class CommentListAdapter extends ArrayAdapter<CommentData> {

        private ArrayList<CommentData> mCommentData;

        public CommentListAdapter(Context context, int resource, ArrayList<CommentData> commentData) {
            super(context, resource, commentData);

            mCommentData = commentData;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_comment, null, true);

            TextView txtNickname = (TextView) rowView.findViewById(R.id.Nickname);
            TextView txtDate = (TextView) rowView.findViewById(R.id.Date);
            TextView txtContent = (TextView) rowView.findViewById(R.id.Content);

            txtNickname.setText(mCommentData.get(position).getNickname());
            txtDate.setText(mCommentData.get(position).getDate());
            txtContent.setText(mCommentData.get(position).getContent());

            return rowView;
//            return super.getView(position, convertView, parent);
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String text = null;

        switch (item.getItemId()) {
            case R.id.move_boardList_btn:
                text = "click the move button";
                Intent intent = new Intent(BoardReadingActivity.this, NoticeBoardActivity.class);
                startActivityForResult(intent, 1000);

                break;
//            case R.id.notice_btn:
//                text = "click the notice button";
//                break;
//            case R.id.bookmark_btn:
//                text = "click the bookmark button";
//                break;
            case R.id.setting_btn:
                text = "click the setting button";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        return true;
    }
}