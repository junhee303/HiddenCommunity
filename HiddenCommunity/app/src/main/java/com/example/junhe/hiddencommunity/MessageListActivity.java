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

public class MessageListActivity extends AppCompatActivity {
    private CustomJsonRequest request_messageList;
    private ArrayList<String> message_otherNicknameList = new ArrayList();
    Context mContext = this;

    private Button bHome;
    private Button bMessage;
    private Button bSearch;
    private Button bNotice;
    private Button bWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        bHome = (Button) findViewById(R.id.action_home);
        bMessage = (Button) findViewById(R.id.action_message);
        bSearch = (Button) findViewById(R.id.action_search);
        bNotice = (Button) findViewById(R.id.action_notice);
        bWrite = (Button) findViewById(R.id.action_write);

        getMessageList(); // 서버에 채팅목록 요청하기
        pushBottomIcon(); // 하단 아이콘 클릭 시 액션
    }

    private class MessageListAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mMessage_otherNicknameList;

        public MessageListAdapter(Context context, int resource, ArrayList<String> messageOtherNicknameList) {
            super(context, resource, messageOtherNicknameList);

            System.out.println("MessageListAdapter 부분");
            mMessage_otherNicknameList = messageOtherNicknameList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_message, null, true);

            TextView txtOtherNickname = (TextView) rowView.findViewById(R.id.message_otherNickname);

            txtOtherNickname.setText(mMessage_otherNicknameList.get(position));

            System.out.println(" getView : " +mMessage_otherNicknameList.get(position));
            return rowView;
        }
    }

    // 서버에 채팅목록 요청하기
    public void getMessageList() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String myNickname = test.getString("UserNickname", null);
        System.out.println("내 닉네임 : " + myNickname);

        // 서버로 내 닉네임 전달
        try {
            String url = "http://52.78.207.133:3000/messages/myList/";
            url += URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url);
            sendRequest_messageList(url); // 서버에서 채팅목록 읽어오기

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //서버에서 채팅목록 읽어오기
    public void sendRequest_messageList(String url) {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();
        request_messageList = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sendRequest_messageList의 onResponse 부분");
                JsonParser js = new JsonParser();
                message_otherNicknameList = js.getMessageList(response);
                System.out.println("JsonParser의 response 받아서 message_otherNicknameList 넣기");
                int messageOther_count = message_otherNicknameList.size();
                System.out.println("해당 채팅목록 상대방 messageOther_count " + messageOther_count);

                // ListView 가져오기
                final ListView messageListView = (ListView) findViewById(R.id.messageListView);

                MessageListAdapter adapter = new MessageListAdapter(mContext, 0, message_otherNicknameList);
                // ListView에 각각의 채팅방를 제어하는 Adapter를 설정
                messageListView.setAdapter(adapter);

                // 채팅방 클릭시 이벤트 리스너 등록
                messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO 아이템 클릭시에 구현할 내용은 여기에.
                        Intent intent = new Intent(getApplicationContext(), MessageRoomActivity.class);

                        // 채팅방 선택시 MessageRoomActivity에 상대방 닉네임 전달
                        String otherNickname = message_otherNicknameList.get(position);
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
        queue.add(request_messageList);
    }

    // 하단 아이콘 클릭 시 액션
    public void pushBottomIcon() {
        bHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '홈 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(MessageListActivity.this, "자유 게시판으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageListActivity.this, BoardRecyclerViewActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '말풍선 아이콘' 클릭 시 쪽지 보내기 메뉴로 이동
                Toast.makeText(MessageListActivity.this, "쪽지 보내기 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageListActivity.this, MessageListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '검색 아이콘' 클릭 시 게시글 검색 메뉴로 이동
                Toast.makeText(MessageListActivity.this, "검색 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageListActivity.this, BoardSearchActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bNotice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '알림 아이콘' 클릭 시 게시글 알림 메뉴로 이동
                Toast.makeText(MessageListActivity.this, "알림 메뉴로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageListActivity.this, NoticeListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        bWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 '글쓰기 아이콘' 클릭 시 게시글 목록으로 이동
                Toast.makeText(MessageListActivity.this, "게시물 작성하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageListActivity.this, BoardWritingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
}
