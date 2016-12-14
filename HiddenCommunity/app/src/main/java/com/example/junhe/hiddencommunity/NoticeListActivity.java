package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

public class NoticeListActivity  extends AppCompatActivity {
    private CustomJsonRequest request_noticeList;
    private ArrayList<Notice> notice_List = new ArrayList();
    Context mContext = this;

    private Button bHome;
    private Button bMessage;
    private Button bSearch;
    private Button bNotice;
    private Button bWrite;

    private int notice_List_position;
    String url_check, result_check;

    class NewNoticeCheckTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(String... params) {
            try {
                result_check = HTTPInstance.Instance().Post(url_check);
                onResponseHttp(result_check);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    NewNoticeCheckTask newNoticeCheckTask;

    private void onResponseHttp(String s){
        if(s == null) {
            System.out.println("응답을 받지 못하였습니다.");
            return;
        }
        Log.d("RESPONSE", s);
        if(s.compareTo("ok")==0){
            System.out.println("새로운 알림을 읽었습니다");

            // 알림 선택시 BoardReadingActivity에 해당 게시글 boardId 전달
            Intent intent = new Intent(getApplicationContext(), BoardReadingActivity.class);
            String boardId = notice_List.get(notice_List_position).boardId;
            intent.putExtra("boardId", boardId);
            startActivityForResult(intent, 1000);
        } else {
            System.out.println("알림을 확인하지 못하였습니다다");
        }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        bHome = (Button) findViewById(R.id.action_home);
        bMessage = (Button) findViewById(R.id.action_message);
        bSearch = (Button) findViewById(R.id.action_search);
        bNotice = (Button) findViewById(R.id.action_notice);
        bWrite = (Button) findViewById(R.id.action_write);

        getNoticeList(); // 서버에 알림목록 요청하기
        pushBottomIcon(); // 하단 아이콘 클릭 시 액션
    }

    private class NoticeListAdapter extends ArrayAdapter<Notice> {

        private ArrayList<Notice> mNotice_List;

        public NoticeListAdapter(Context context, int resource, ArrayList<Notice> noticeList) {
            super(context, resource, noticeList);

            System.out.println("NoticeListAdapter 부분");
            mNotice_List = noticeList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_notice, null, true);
           // View rowView2 = inflater.inflate(R.layout.list_notice_like, null, true);

            ImageView noticeIcon = (ImageView) rowView.findViewById(R.id.noticeIcon);
            TextView actionAuthor = (TextView) rowView.findViewById(R.id.actionAuthor);
            TextView noticeText = (TextView) rowView.findViewById(R.id.noticeText);
            ImageView newIcon = (ImageView) rowView.findViewById(R.id.newIcon);
            TextView noticeDate = (TextView) rowView.findViewById(R.id.noticeDate);

            actionAuthor.setText(mNotice_List.get(position).actionAuthor);
            noticeDate.setText(mNotice_List.get(position).date);

            System.out.println(" getView : " +mNotice_List.get(position).actionAuthor);

            String contentType = mNotice_List.get(position).type;
            System.out.println(" contentType : " + mNotice_List.get(position).type + " / " + contentType );

            // 알림 type에 따라 comment 아이콘 또는 like 아이콘 배치
            if(contentType.equals("comment")) { // comment
                noticeIcon.setBackgroundResource(R.drawable.ic_notice_comment);
                noticeText.setText("님이 댓글을 남겼습니다");
                System.out.println("알림 타입이 댓글");
            } else if(contentType.equals("like")) { // like
                noticeIcon.setBackgroundResource(R.drawable.ic_notice_like);
                noticeText.setText("님이 회원님의 글을 좋아합니다");
                System.out.println("알림 타입이 좋아요");
            }

            if(mNotice_List.get(position).getCheck() == false) { // 새로운 메시지
                newIcon.setBackground(this.getContext().getResources().getDrawable(R.drawable.newnew));
            }


            return rowView;
        }
    }

    // 서버에 알림목록 요청하기
    public void getNoticeList() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String myNickname = test.getString("UserNickname", null);
        System.out.println("내 닉네임 : " + myNickname);

        // 서버로 내 닉네임 전달
        try {
            String url_notice = "http://52.78.207.133:3000/notices/list/";
            url_notice += URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url_notice);
            sendRequest_noticeList(url_notice); // 서버에서 알림목록 읽어오기

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //서버에서 알림목록 읽어오기
    public void sendRequest_noticeList (final String url_notice) {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();
        request_noticeList = new CustomJsonRequest(Request.Method.GET,
                url_notice, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sendRequest_noticeList의 onResponse 부분");
                JsonParser js = new JsonParser();
                notice_List = js.getNoticeList(response);
                System.out.println("JsonParser의 response 받아서 notice_List 넣기");
                int notice_count = notice_List.size();
                System.out.println("해당 채팅목록 상대방 notice_count " + notice_count);

                // ListView 가져오기
                final ListView noticeListView = (ListView) findViewById(R.id.noticeListView);

                NoticeListAdapter adapter = new NoticeListAdapter(mContext, 0, notice_List);
                // ListView에 각각의 채팅방를 제어하는 Adapter를 설정
                noticeListView.setAdapter(adapter);

                // 알림 클릭시 이벤트 리스너 등록
                noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO 아이템 클릭시에 구현할 내용은 여기에.
                        notice_List_position = position;

                        // url로 해당 알림id 보내면서 check를 false -> true 로 바꾸기


                        // 서버로 해당 알림이 울린 boardId 전달
                        try {
                            String noticeId = notice_List.get(notice_List_position).noticeId;

                            url_check = "http://52.78.207.133:3000/notices/check/";
                            url_check += URLEncoder.encode(noticeId, "utf-8");

                            Log.d("url", url_check);

                            newNoticeCheckTask = new NewNoticeCheckTask();
                            newNoticeCheckTask.execute();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // queue에 Request를 추가해준다.
        queue.add(request_noticeList);
    }

    // 하단 아이콘 클릭 시 액션
    public void pushBottomIcon() {
        bHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '홈 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(NoticeListActivity.this, "자유 게시판으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoticeListActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '말풍선 아이콘' 클릭 시 대화하기 메뉴로 이동
                Toast.makeText(NoticeListActivity.this, "대화하기 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoticeListActivity.this, MessageListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '검색 아이콘' 클릭 시 게시글 검색 메뉴로 이동
                Toast.makeText(NoticeListActivity.this, "검색 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoticeListActivity.this, BoardSearchActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bNotice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '알림 아이콘' 클릭 시 게시글 알림 메뉴로 이동
                Toast.makeText(NoticeListActivity.this, "알림 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoticeListActivity.this, NoticeListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '글쓰기 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(NoticeListActivity.this, "게시물 작성하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoticeListActivity.this, BoardWritingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
}