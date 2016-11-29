package com.example.junhe.hiddencommunity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    final Context context = this;
    String url_boardId, result_readBoard;
    String url_comment, result_readComment;

    private ArrayList<CommentData> comment_data = new ArrayList<CommentData>();
    Context mContext = this;

    ListView comment_list;
    ScrollView scrollView;

    // 게시글 읽어오기
    class PostReadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                result_readBoard = HTTPInstance.Instance().Post(url_boardId);
                Log.d("doInBackground result:", result_readBoard);
                onResponseHttp1(result_readBoard);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
    PostReadingTask postReadingTask;
    // 게시글 읽어오기 response
    private void onResponseHttp1(String s) {
        System.out.println("onResponseHttp의 String s 값 : " + s);
        if (s == null) {
//            mProgressDialog = ProgressDialog.show(.this,"",
//                    "잠시만 기다려 주세요",true);
            System.out.println("작성한 글을 받아오지 못하였습니다.");
            return;
        }
        Log.d("board", s);
        if (s != null) {
            System.out.println("작성한 글을 받아왔습니다.");
            System.out.println(s);
        } else {

        }
    }

    // 댓글 읽어오기
    class CommentReadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                result_readComment = HTTPInstance.Instance().Post(url_comment);
                Log.d("doInBackground result:", result_readComment);
                onResponseHttp2(result_readComment);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    CommentReadingTask commentReadingTask;
    // 댓글 읽어오기 response
    private void onResponseHttp2(String s) {
        System.out.println("댓글 작성 응답 : " + s);
        if (s == null) {
//            mProgressDialog = ProgressDialog.show(.this,"",
//                    "잠시만 기다려 주세요",true);
            System.out.println("작성한 댓글이 등록되습니다.");
            return;
        }
        Log.d("board", s);
        if (s != null) {
            System.out.println("작성한 글을 받아왔습니다.");
            System.out.println(s);
        } else {

        }
    }

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

//        top_toolbar = (Toolbar) findViewById(R.id.top_toolbar);
//        setSupportActionBar(top_toolbar);
//        bottom_toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
//        setSupportActionBar(bottom_toolbar);

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

        //url_boardId = "http://52.78.207.133:3000/boards/read/" + boardId;

//        postReadingTask = new PostReadingTask();
//        postReadingTask.execute();

        sendRequest_board(); // 게시물 읽어오기
        pushLikeButton(); // 좋아요 버튼 클릭
        pushCommentButton(); // 댓글 쓰기 버튼 클릭

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
        String url = "http://52.78.207.133:3000/boards/read/" + boardId;
        Log.d("url", url);
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
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
//                        try {
//                            JSONArray postTag = data.getTag();
//                            ArrayList Tag = new ArrayList();
//                            if (Tag != null) {
//                                int len = postTag.length();
//                                for (int i = 0; i < len; i++) {
//                                    Tag.add(postTag.get(i).toString());
//                                }
//                                String tags = Tag.get(0);
//                            }
//
//
//                        }catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        System.out.println("BoardData에서 받아와서 Title에 getText : " +Title.getText());
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
                // 좋아요 클릭 시 까만 하트
                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;
                }
                count_like--;
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 좋아요 해제 시 하얀 하트
                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;
                }
                count_like++;
            }
        });
        TheNumberOfLike.setText("좋아요 수 : " + count_like + "click_like 클릭 상태 : " + click_like);
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
                            sendCommentToServer(); // 작성한 댓글 서버로 보내기
                                //comment_data.add(new CommentData("ID", nickname, "날짜", userInput.getText().toString()));

                            sendRequest_comment(); // 댓글 읽어오기
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

    // 작성한 댓글 서버로 보내기
    public void sendCommentToServer() {

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String comment_author = test.getString("UserNickname", null);
        String comment_body = userInput.getText().toString();
        System.out.println("comment_author는 " + comment_author + " / comment_body는" + comment_body);

        Bundle extras = getIntent().getExtras();
        String boardId = extras.getString("boardId");
        url_comment = "http://52.78.207.133:3000/boards/comment/"+ boardId;
        try {
            url_comment += "author=" + URLEncoder.encode(comment_author, "utf-8");
            url_comment += "&body=" + URLEncoder.encode(comment_body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 댓글 읽어오기
    public void sendRequest_comment() {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        Bundle extras = getIntent().getExtras();
        String boardId = extras.getString("boardId");
        Log.d("sendRequest의 boardId: ", boardId);
        String url = "http://52.78.207.133:3000/boards/read/" + boardId;

        // 서버에 댓글 요청
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sendRequest의 onResponse 부분");
                JsonParser js = new JsonParser();
                js.getComment(response);

                // ListView 가져오기
                comment_list = (ListView) findViewById(R.id.comment_list);
                CommentListAdapter adapter = new CommentListAdapter(mContext, 0, comment_data);
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
        // queue에 Request를 추가해준다.
        queue.add(request);
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

            TextView txtAuthor = (TextView) rowView.findViewById(R.id.Author);
            TextView txtDate = (TextView) rowView.findViewById(R.id.Date);
            TextView txtBody = (TextView) rowView.findViewById(R.id.Body);

            txtAuthor.setText(mCommentData.get(position).getAuthor());
            txtDate.setText(mCommentData.get(position).getDate());
            txtBody.setText(mCommentData.get(position).getBody());

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
                // 상단바의 화살표 버튼 클릭 시 게시글 목록으로 나가기
                text = "click the move button";
                Intent intent = new Intent(BoardReadingActivity.this, SwipeBoardActivity.class);
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
