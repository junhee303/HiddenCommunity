package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import data.BoardData;

public class BoardSearchActivity extends AppCompatActivity {
    private Button move_boardList_btn;
    private TextView toolbar_textview;
    private EditText enter_search;
    private Button bSearch;
    private RecyclerView recyclerView;

    ArrayList<String> mBoardId = new ArrayList<>();
    ArrayList<String> mCategory = new ArrayList<>();
    ArrayList<String> mTitleSet = new ArrayList<>();
    ArrayList<String> mAuthorSet = new ArrayList<>();
    ArrayList<String> mDateSet = new ArrayList<>();
    ArrayList<String> mBodySet = new ArrayList<>();
    ArrayList<String> mTagSet = new ArrayList<>();
    ArrayList<Integer> mHitSet = new ArrayList<>();
    ArrayList<Integer> mLikeSet = new ArrayList<>();
    ArrayList<Integer> mCommentSet = new ArrayList<>();

    Board_Adapter mAdapter;
    String keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_search);

        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        enterSearchKeyword(); // 검색어 입력 후 [ 검색 ] 버튼 클릭
        pushBoardListButton(); // 상단의 [ < ] 버튼 클릭 -> 게시글 목록 화면으로 이동

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mBoardId.clear();
        mTitleSet.clear();
        mCategory.clear();
        mAuthorSet.clear();
        mDateSet.clear();
        mBodySet.clear();
        mTagSet.clear();
        mHitSet.clear();
        mLikeSet.clear();
        mCommentSet.clear();

        mAdapter = new Board_Adapter(getApplicationContext(), mCategory, mBoardId, mTitleSet, mAuthorSet, mDateSet, mBodySet, mTagSet, mHitSet, mLikeSet, mCommentSet);
        recyclerView.setAdapter(mAdapter);
    }

    // 검색어 입력 후 [ 검색 ] 버튼 클릭
    public void enterSearchKeyword() {
        move_boardList_btn = (Button) findViewById(R.id.move_boardList_btn);
        toolbar_textview = (TextView) findViewById(R.id.toolbar_textview);
        enter_search = (EditText) findViewById(R.id.enter_search);
        bSearch = (Button) findViewById(R.id.bSearch);

        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (enter_search.getText().toString().length() == 0) {
                    Toast.makeText(BoardSearchActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                    enter_search.requestFocus();
                    return;
                }
                keyword = enter_search.getText().toString();
                System.out.println("검색어는 " + keyword);
                sendRequest_searchList(keyword);
            }
        });
    }

    // 검색 결과 받아오기 - JsonObject
    public void sendRequest_searchList(String keyword) {

        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        // 서버로 카테고리 전달
        try {
            String url_keyword = "http://52.78.207.133:3000/boards/search/";
            url_keyword += URLEncoder.encode(keyword, "utf-8");
            Log.d("url", url_keyword);

            CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                    url_keyword, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("sendRequest의 onResponse 부분");
                    JsonParser js = new JsonParser();

                    ArrayList<BoardData> data_search = js.getBoardData(response);

                    System.out.println("JsonParser의 response 받아서 data_search에 넣기");
                    System.out.println(data_search.size());

                    for (int i = 0; i < data_search.size(); i++) {
                        System.out.println("data.get(i).getBoardId() : " + data_search.get(i).getBoardId());
                        mBoardId.add(data_search.get(i).getBoardId());
                        mCategory.add(data_search.get(i).getCategory());
                        mTitleSet.add(data_search.get(i).getTitle());
                        mAuthorSet.add(data_search.get(i).getAuthor());
                        mDateSet.add(data_search.get(i).getDate());
                        mBodySet.add(data_search.get(i).getBody());
                        mTagSet.add(data_search.get(i).getTag());
                        mHitSet.add(data_search.get(i).getHit());
                        mLikeSet.add(data_search.get(i).getLike());
                        mCommentSet.add(data_search.get(i).getComment());
                    }
                    System.out.println("BoardData에서 받아와서 mAuthorSet에 add : " + mAuthorSet);
                    mAdapter.update();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
            // queue에 Request를 추가해준다.
            queue.add(request);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 상단이 [ < ] 아이콘 클릭 시
    public void pushBoardListButton() {
        move_boardList_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 [ < ] 버튼 클릭 시 게시글 목록으로 이동
                Toast.makeText(BoardSearchActivity.this, "게시글 목록으로 이동", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BoardSearchActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
}