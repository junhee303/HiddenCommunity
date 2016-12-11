package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by junhe on 2016-12-11.
 */

public class ChatListActivity extends AppCompatActivity {
    private CustomJsonRequest request_chatList;
    private ArrayList<String> chat_otherNicknameList = new ArrayList();
    Context mContext = this;

    private Button bHome;
    private Button bChat;
    private Button bSearch;
    private Button bNotice;
    private Button bWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        bHome = (Button) findViewById(R.id.action_home);
        bChat = (Button) findViewById(R.id.action_chat);
        bSearch = (Button) findViewById(R.id.action_search);
        bNotice = (Button) findViewById(R.id.action_notice);
        bWrite = (Button) findViewById(R.id.action_write);

        getChatList(); // 서버에 채팅목록 요청하기
        pushBottomIcon(); // 하단 아이콘 클릭 시 액션
    }

    private class ChatListAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mChat_otherNicknameList;

        public ChatListAdapter(Context context, int resource, ArrayList<String> chatOtherNicknameList) {
            super(context, resource, chatOtherNicknameList);

            System.out.println("ChatListAdapter 부분");
            mChat_otherNicknameList = chatOtherNicknameList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_chat, null, true);

            TextView txtOtherNickname = (TextView) rowView.findViewById(R.id.chat_otherNickname);

            txtOtherNickname.setText(mChat_otherNicknameList.get(position));

            System.out.println(" getView : " +mChat_otherNicknameList.get(position));
            return rowView;
        }
    }

    // 서버에 채팅목록 요청하기
    public void getChatList() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String myNickname = test.getString("UserNickname", null);
        System.out.println("내 닉네임 : " + myNickname);

        // 서버로 내 닉네임 전달
        try {
            String url = "http://52.78.207.133:3000/messages/myList/";
            url += URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url);
            sendRequest_chatList(url); // 서버에서 채팅목록 읽어오기

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //서버에서 채팅목록 읽어오기
    public void sendRequest_chatList (String url) {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();
        request_chatList = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sendRequest_chatList의 onResponse 부분");
                JsonParser js = new JsonParser();
                chat_otherNicknameList = js.getChatList(response);
                System.out.println("JsonParser의 response 받아서 chat_otherNicknameList 넣기");
                int chatOther_count = chat_otherNicknameList.size();
                System.out.println("해당 채팅목록 상대방 chatOther_count " + chatOther_count);

                // ListView 가져오기
                final ListView chatListView = (ListView) findViewById(R.id.chatListView);

                ChatListAdapter adapter = new ChatListAdapter(mContext, 0, chat_otherNicknameList);
                // ListView에 각각의 채팅방를 제어하는 Adapter를 설정
                chatListView.setAdapter(adapter);

                // 채팅방 클릭시 이벤트 리스너 등록
                chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO 아이템 클릭시에 구현할 내용은 여기에.
                        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);

                        // 채팅방 선택시 ChatRoomActivity에 상대방 닉네임 전달
                        String otherNickname = chat_otherNicknameList.get(position);
                        intent.putExtra("Author", otherNickname);
                        startActivityForResult(intent, 1000);
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
        queue.add(request_chatList);
    }

    // 하단 아이콘 클릭 시 액션
    public void pushBottomIcon() {
        bHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '홈 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(ChatListActivity.this, "자유 게시판으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatListActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '채팅 아이콘' 클릭 시 채팅 메뉴로 이동
                Toast.makeText(ChatListActivity.this, "채팅 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatListActivity.this, ChatListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '검색 아이콘' 클릭 시 게시글 검색 메뉴로 이동
                Toast.makeText(ChatListActivity.this, "검색 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatListActivity.this, BoardSearchActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bNotice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '알림 아이콘' 클릭 시 게시글 알림 메뉴로 이동
                Toast.makeText(ChatListActivity.this, "알림 메뉴로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        bWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '글쓰기 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(ChatListActivity.this, "게시물 작성하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatListActivity.this, BoardWritingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
}
