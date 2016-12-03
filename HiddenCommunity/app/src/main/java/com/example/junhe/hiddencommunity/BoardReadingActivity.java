package com.example.junhe.hiddencommunity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.junhe.hiddencommunity.network.CustomJsonRequest;
import com.example.junhe.hiddencommunity.network.JsonParser;
import com.example.junhe.hiddencommunity.network.VolleySingleton;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import data.BoardData;
import data.CommentData;

//import org.apache.http.impl.client.DefaultHttpClient;

public class BoardReadingActivity extends AppCompatActivity {

    private Toolbar top_toolbar;
    private TextView Category;
    private TextView Title;
    private TextView Author;
    private TextView Date;
    private TextView Body;
    private TextView Tag;
    private Button bLikeOn;
    private Button bLikeOff;
    private EditText userInput;
    private Button bAddComment;
    private TextView TheNumberOfLike;
    private boolean click_like = false;
    private int count_like = 0;
    private int count_comment = 0;

    private Button move_boardList_btn;
    private Button setting_btn;

    final Context context = this;

    CustomJsonRequest request_comment;
    ListView comment_list;
    ScrollView scrollView;

    private ArrayList<CommentData> comment_data = new ArrayList<CommentData>();
    Context mContext = this;

    String url, result;

    // 좋아요 버튼 클릭 시 response 받아오기
    class LikeCheckTask extends AsyncTask<String, Void, String> {
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

    LikeCheckTask likeCheckTask;
    // 좋아요 버튼 클릭 시 "ok" response
    private void onResponseHttp(String s) {
        if (s == null) {
            System.out.println("서버에서 전송된 response가 null입니다");
            return;
        }
        Log.d("RESPONSE", s);
        if (s.compareTo("ok") == 0) {
            System.out.println("좋아요 수 변화");
//            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//            startActivityForResult(intent, 1000);
        } else {
            System.out.println("좋아요가 반영되지 않았습니다");
            //Toast.makeText(EmailActivity.this, "다시 인증해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_reading);

        top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        Category = (TextView) findViewById(R.id.Category);
        Title = (TextView) findViewById(R.id.Title);
        Author = (TextView) findViewById(R.id.Author);
        Date = (TextView) findViewById(R.id.Date);
        Body = (TextView) findViewById(R.id.Body);
        Tag = (TextView) findViewById(R.id.Tag);
        bLikeOn = (Button) findViewById(R.id.bLikeOn);
        bLikeOff = (Button) findViewById(R.id.bLikeOff);
        bAddComment = (Button) findViewById(R.id.bAddComment);
        TheNumberOfLike = (TextView) findViewById(R.id.TheNumberOfLike);

        move_boardList_btn = (Button) findViewById(R.id.move_boardList_btn);
        setting_btn = (Button) findViewById(R.id.move_boardList_btn);

        sendRequest_board(); // 게시물 읽어오기
        pushLikeButton(); // 좋아요 버튼 클릭
        pushCommentButton(); // 댓글 쓰기 버튼 클릭
        pushBoardListButton(); // 상단의 [ < ] 버튼 클릭 -> 게시글 목록 화면으로 이동
        pushSettingButton(setting_btn); // 상단의 설정 버튼 클릭 -> 대화하기, 신고하기, 수정하기

    }

    // 게시물 읽어오기
    public void sendRequest_board() {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();
        // RequestQueue를 새로 만들어준다.
        //RequestQueue queue = Volley.newRequestQueue(this);
        // Request를 요청 할 URL
        Bundle extras = getIntent().getExtras();
        String boardId = extras.getString("boardId");
        Log.d("sendRequest의 boardId: ", boardId);
        String url_boardId = "http://52.78.207.133:3000/boards/read/" + boardId;
        Log.d("url", url_boardId);
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                url_boardId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sendRequest의 onResponse 부분");
                JsonParser js = new JsonParser();
                BoardData data = js.getData(response);
                System.out.println("JsonParser의 response 받아서 BoardData에 넣기");
                Category.setText(data.getCategory());
                Title.setText(data.getTitle());
                Author.setText(data.getAuthor());
                Date.setText(data.getDate());
                Body.setText(data.getBody());
                Tag.setText(data.getTag());

                System.out.println("BoardData에서 받아와서 Title에 getText : " + Title.getText());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        // queue에 Request를 추가해준다.
        queue.add(request);
    }

    // 좋아요 버튼 클릭 시 하트 색 변경
    public void pushLikeButton() {
        bLikeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 좋아요 해제 시 하얀 하트
                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;

                    // 좋아요 수 1-- 서버 보내기
                    Bundle extras = getIntent().getExtras();
                    String boardId = extras.getString("boardId");
                    url = "http://52.78.207.133:3000/boards/unlike/" + boardId;

                    Log.d("url", url);
                    likeCheckTask = new LikeCheckTask();
                    likeCheckTask.execute();
                }
                count_like--;
                TheNumberOfLike.setText("좋아요 수 : " + count_like + "click_like 클릭 상태 : " + click_like);
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 좋아요 클릭 시 검정 하트
                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;

                    // 좋아요 수 1++ 서버 보내기
                    Bundle extras = getIntent().getExtras();
                    String boardId = extras.getString("boardId");
                    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                    String author = test.getString("UserNickname", null);
                    try {
                        url = "http://52.78.207.133:3000/boards/like/" + boardId + "?";
                        url += "&author=" + URLEncoder.encode(author, "utf-8");
                        Log.d("url", url);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    likeCheckTask = new LikeCheckTask();
                    likeCheckTask.execute();
                }
                count_like++;
                TheNumberOfLike.setText("좋아요 수 : " + count_like + "click_like 클릭 상태 : " + click_like);
            }
        });
    }

    // 댓글 작성 버튼
    public void pushCommentButton() {
        bAddComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View commentPopupView = li.inflate(R.layout.comment_post_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(commentPopupView);

                userInput = (EditText) commentPopupView.findViewById(R.id.editTextDialogComment);

                // 작성한 댓글 내용 입력
                alertDialogBuilder.setCancelable(false).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        if (userInput.getText().toString().length() == 0) {
                            Toast.makeText(BoardReadingActivity.this, "작성한 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                            userInput.requestFocus();

                        } else {
                            sendRequest_comment(); // 댓글 서버에 등록하고 댓글리스트 읽어오기
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

    // 댓글 보내고 읽어오기
    public void sendRequest_comment() {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String comment_author = test.getString("UserNickname", null);
        String comment_body = userInput.getText().toString();
        System.out.println("comment_author는 " + comment_author + " / comment_body는" + comment_body);

        try {
            Bundle extras = getIntent().getExtras();
            String boardId = extras.getString("boardId");
            String url_comment = "http://52.78.207.133:3000/boards/comment/" + boardId + "?";
            url_comment += "author=" + URLEncoder.encode(comment_author, "utf-8");
            url_comment += "&body=" + URLEncoder.encode(comment_body, "utf-8");
            Log.d("url", url_comment);

            // 서버에 댓글 요청
            request_comment = new CustomJsonRequest(Request.Method.GET,
                    url_comment, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("sendRequest의 onResponse 부분");
                    JsonParser js = new JsonParser();
                    CommentData data = js.getComment(response);

                    // ListView 가져오기
                    comment_list = (ListView) findViewById(R.id.comment_list);
                    CommentListAdapter adapter = new CommentListAdapter(mContext, 0, comment_data);
                    adapter.add(data);
                    // ListView에 각각의 전공표시를 제어하는 Adapter를 설정
                    comment_list.setAdapter(adapter);

                    setListViewHeightBasedOnChildren(comment_list); // 댓글 리스트뷰 높이만큼 다 보이게 세팅

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // queue에 Request를 추가해준다.
        queue.add(request_comment);
    }

    // 댓글 Adapter
    private class CommentListAdapter extends ArrayAdapter<CommentData> {

        private ArrayList<CommentData> mCommentData;

        public CommentListAdapter(Context context, int resource, ArrayList<CommentData> commentData) {
            super(context, resource, commentData);

            mCommentData = commentData;
        }

        // 댓글 리스트 보이기
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView cmtAuthor;
            TextView cmtDate;
            TextView cmtBody;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_comment, parent, false);

                cmtAuthor = (TextView) convertView.findViewById(R.id.comment_Author);
                cmtDate = (TextView) convertView.findViewById(R.id.comment_Date);
                cmtBody = (TextView) convertView.findViewById(R.id.comment_Body);

                convertView.setTag(cmtAuthor);
                convertView.setTag(cmtDate);
                convertView.setTag(cmtBody);
            } else {
                cmtAuthor = (TextView) convertView.getTag();
                cmtDate = (TextView) convertView.getTag();
                cmtBody = (TextView) convertView.getTag();
            }

            System.out.println(position);
            cmtAuthor.setText(mCommentData.get(position).getAuthor().toString());
            cmtDate.setText(mCommentData.get(position).getDate().toString());
            cmtBody.setText(mCommentData.get(position).getBody().toString());

            return convertView;
        }

    }

    // 댓글 수에 따라 리스트 높이 조절
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

    // 상단이 [ < ] 아이콘 클릭 시
    public void pushBoardListButton() {
        move_boardList_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 [ < ] 버튼 클릭 시 게시글 목록으로 이동
                Toast.makeText(BoardReadingActivity.this, "게시글 목록으로 이동", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BoardReadingActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    // 상단이 설정 아이콘 클릭 시
    public void pushSettingButton(View button) {

        PopupMenu popUp = new PopupMenu(this, button);
        popUp.getMenuInflater().inflate(R.menu.board_reading_menu, popUp.getMenu());
        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                String txtAuthor = test.getString("UserNickname", null);

                switch (item.getItemId()) {
                    case R.id.chat_btn:
                        Toast.makeText(BoardReadingActivity.this, "채팅하기 기능이 구현되지 않았습니다", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.report_btn:
                        Toast.makeText(BoardReadingActivity.this, "해당 게시글을 신고하였습니다", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.update_btn:
                        if (Author.getText().equals(txtAuthor)) {
                            Toast.makeText(BoardReadingActivity.this, "click the setting button", Toast.LENGTH_SHORT).show();

                            Intent intent2 = new Intent(BoardReadingActivity.this, BoardUpdateActivity.class);
                            Bundle extras = getIntent().getExtras();
                            String boardId = extras.getString("boardId", null);
                            String txtCategory = Category.getText().toString();
                            String txtTitle = Title.getText().toString();
                            String txtBody = Body.getText().toString();
                            String txtTag = Tag.getText().toString();
                            intent2.putExtra("boardId", boardId);
                            intent2.putExtra("txtCategory", txtCategory);
                            intent2.putExtra("txtTitle", txtTitle);
                            intent2.putExtra("txtBody", txtBody);
                            intent2.putExtra("txtTag", txtTag);
                            startActivityForResult(intent2, 1000);
                        } else {
                            Toast.makeText(BoardReadingActivity.this, "자신의 글만 수정할 수 있습니다", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

    }


}


