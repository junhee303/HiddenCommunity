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
import com.android.volley.toolbox.Volley;
import com.example.junhe.hiddencommunity.network.CustomJsonRequest;
import com.example.junhe.hiddencommunity.network.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
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
    private Button bAddComment;
    private TextView TheNumberOfLike;
    private boolean click_like = false;
    private int count_like = 0;
    private int count_comment = 0;

    final Context context = this;

    String url_boardId, result;

    private ArrayList<CommentData> comment_data = new ArrayList<CommentData>();
    Context mContext = this;

    ListView comment_list;
    ScrollView scrollView;

    class PostReadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                result = HTTPInstance.Instance().Post(url_boardId);
                Log.d("doInBackground result:", result);
                onResponseHttp(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}

    PostReadingTask postReadingTask;

    private void onResponseHttp(String s) {
        System.out.println("onResponseHttp의 String s 값 : " + s);
        if (s == null) {
//            mProgressDialog = ProgressDialog.show(.this,"",
//                    "게시물을 받아오지 못하였습니다.",true);
            System.out.println("게시물을 받아오지 못하였습니다.");
            return;
        }
        Log.d("board", s);
        if (s != null) {
            System.out.println("게시물을 받아왔습니다.");
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

        sendRequest();
        pushLikeButton(); // ?넫?뿭釉???뜝?룞?삕
        pushCommentButton(); // ??뜝?룞?삕?뜝占?? ??뜝?룞?삕?뜝占??

    }

    public void sendRequest() {
        // RequestQueue를 새로 만들어준다.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request를 요청 할 URL
        Bundle extras = getIntent().getExtras();
        String boardId = extras.getString("boardId");
        Log.d("sendRequest의 boardId: ", boardId);
        String url = "http://52.78.207.133:3000/boards/read/" + boardId;

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

    public void pushLikeButton() {
        bLikeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // ?넫?뿭釉???뜝?룞?삕 ??뜝?룞?삕?뜝占?? ??뜝?룞?삕 繹먮슢彛? ??뜝?룞?삕??뜝?룞?삕

                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;
                }
                count_like--;
                TheNumberOfLike.setText("?넫?뿭釉???뜝?룞?삕 ??뜝?룞?삕 = " + count_like + "click_like ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕" + click_like);
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // ?넫?뿭釉???뜝?룞?삕 ??뜝?룞?삕??뜝?룞?삕 ??뜝?룞?삕 ??뜝?룞?삕?? ??뜝?룞?삕??뜝?룞?삕

                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;
                }
                count_like++;
                TheNumberOfLike.setText("?넫?뿭釉???뜝?룞?삕 ??뜝?룞?삕 = " + count_like + "click_like ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕" + click_like);
            }
        });
    }

    public void pushCommentButton() {
        bAddComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View commentPopupView = li.inflate(R.layout.comment_post_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(commentPopupView); // ??뜝?룞?삕??뜝?룞?삕筌≪럩?몵?뜝占?? ??뜝?룞?삕?뜝占?? ??뜝?룞?삕?뜝占??

                final EditText userInput = (EditText) commentPopupView.findViewById(R.id.editTextDialogComment);

                // ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕 ??뜝?룞?삕?뜝占?? ??뜝?룞?삕??뜝?룞?삕 ??뜝?룞?삕??뜝?룞?삕
                alertDialogBuilder.setCancelable(false).setPositiveButton("??뜝?룞?삕?뜝占??", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        if (userInput.getText().toString().length() == 0) {
                            Toast.makeText(BoardReadingActivity.this, "??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕 ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕 ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕", Toast.LENGTH_SHORT).show();
                            userInput.requestFocus();

                        } else {
                            SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                            String nickname = test.getString("UserNickname", null);

                            if (nickname != null) {
                                comment_data.add(new CommentData(nickname, "??뜝?룞?삕?뜝占??", userInput.getText().toString()));
                            }

                            // ListView ?뜝占????뜝?룞?삕??뜝?룞?삕?뜝占??
                            comment_list = (ListView) findViewById(R.id.comment_list);
                            CommentListAdapter adapter = new CommentListAdapter(mContext, 0, comment_data);
                            // ListView??뜝?룞?삕 揶쏄낫而???뜝?룞?삕 ??뜝?룞?삕?⑤벏紐???뜝?룞?삕?뜝占?? ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕 Adapter?뜝占?? ??뜝?룞?삕??뜝?룞?삕
                            comment_list.setAdapter(adapter);

                            setListViewHeightBasedOnChildren(comment_list); // ??뜝?룞?삕?뜝占?? ?뵳?딅뮞??뜝?룞?삕?뜝占?? ??뜝?룞?삕??뜝?룞?삕筌띾슦寃? ??뜝?룞?삕 癰귣똻?뵠?뜝占?? ??뜝?룞?삕??뜝?룞?삕
                        }
                    }
                })
                        .setNegativeButton("?뿆?뫁?꺖",
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
                // ??뜝?룞?삕??뜝?룞?삕獄쏅뗄?벥 ??뜝?룞?삕??뜝?룞?삕??뜝?룞?삕 甕곌쑵?뱣 ??뜝?룞?삕?뜝占?? ??뜝?룞?삕 野껊슣?뻻?뜝占?? 筌뤴뫖以???뜝?룞?삕?뜝占?? ??뜝?룞?삕?뜝占???뜝占??
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
