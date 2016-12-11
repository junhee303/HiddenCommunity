package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChatRoomActivity extends AppCompatActivity {
    private CustomJsonRequest request_chat;
//    ArrayList<ChatMessage> chat_message;
//    private int chat_count; // chat_message의 size
    private ChatMessageAdapter chatMessageAdapter;
    private TextView Date;
    private EditText etMsg;
    private Button buttonSend;
    private Button move_chatList_btn;
    Intent intent;

    private String recipient;
    String myNickname; // 내 닉네임

    //String url, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        move_chatList_btn = (Button) findViewById(R.id.move_chatList_btn);
        TextView otherNickname = (TextView) findViewById(R.id.toolbar_textview);
        Date = (TextView) findViewById(R.id.Date);
        buttonSend = (Button) findViewById(R.id.send_btn);
        etMsg = (EditText) findViewById(R.id.chatText);
        // message_send = (TextView) findViewById(R.id.message_send);

        Bundle extras = getIntent().getExtras();
        recipient = extras.getString("Author");
        otherNickname.setText(recipient);

        sendRequest_sumCchatData(); // 채팅방 입장시 이전 채팅 읽어오기
        pushBoardListButton(); // 상단의 [ < ] 아이콘 클릭 시

        // 전송 버튼 클릭
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etMsg.getText().toString().length() == 0) {
                    Toast.makeText(ChatRoomActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    etMsg.requestFocus();
                    return;
                } else {
                    sendChatMessage(); // 메세지 내용 전송
                    etMsg.setText("");
                    etMsg.requestFocus();
                    return;
                }
            }
        });

    }

    // 채팅방 입장시 이전 채팅 읽어오기
    public void sendRequest_sumCchatData() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        myNickname = test.getString("UserNickname", null);

        System.out.println("채팅방 입장시 이전 채팅 읽어오기");

        // 서버로 대화 전달
        try {
            String url = "http://52.78.207.133:3000/messages/room/";
            url += URLEncoder.encode(recipient, "utf-8");
            url += "/" + URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url);
            sendRequest_chat(url); // 서버에서 채팅 내용 읽어오기

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 메세지 내용 전송
    public void sendChatMessage() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        myNickname = test.getString("UserNickname", null);

        String strMsg = etMsg.getText().toString();
        System.out.println("전송할 메세지 내용 : " + strMsg); // ok

        // 서버로 대화 전달
        try {
            String url = "http://52.78.207.133:3000/messages/send/";
            url += URLEncoder.encode(recipient, "utf-8")+"?";
            url += "&body=" + URLEncoder.encode(strMsg, "utf-8");
            url += "&sender=" + URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url);
            sendRequest_chat(url); // 서버에서 채팅 내용 읽어오기

            etMsg.setText("");
            etMsg.requestFocus(); // 전송 후 메세지 창 비우기
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //  서버에서 채팅 내용 읽어오기
    public void sendRequest_chat(String url) {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        System.out.println("sendRequest_chat 내부");

        request_chat = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("sendRequest_chat의 onResponse 부분");
                JsonParser js = new JsonParser();
                ArrayList<ChatMessage> chat_message = js.getChat(response, myNickname);
                System.out.println("JsonParser의 response 받아서 chat_message에 넣기");
                int chat_count = chat_message.size();
                System.out.println("해당 채팅방 메세지 갯수인 chat_count " + chat_count);

                chatMessageAdapter = new ChatMessageAdapter(getApplicationContext(), R.layout.chatting_message);

                for(int i = 0; i < chat_count; i++){
                    chatMessageAdapter.add(chat_message.get(i));
                }

                final ListView listView = (ListView) findViewById(R.id.listView);
                listView.setDivider(null);
                listView.setAdapter(chatMessageAdapter);
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // 이게 필수
                // When message is added, it makes listview to scroll last message
                chatMessageAdapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listView.setSelection(chatMessageAdapter.getCount() - 1);
                    }
                });

                chatMessageAdapter.update();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // queue에 Request를 추가해준다.
        queue.add(request_chat);
    }

    // 상단의 [ < ] 아이콘 클릭 시
    public void pushBoardListButton() {
        move_chatList_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 [ < ] 버튼 클릭 시 게시글 목록으로 이동
                Toast.makeText(ChatRoomActivity.this, "대화하기 목록으로 이동", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ChatRoomActivity.this, ChatListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    // 대화 읽어오기
//    public void sendMessage() {
//        VolleySingleton v = VolleySingleton.getInstance();
//        RequestQueue queue = v.getRequestQueue();
//
//
//        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
//        String recipient = otherNickname.getText().toString();
//        String message = enterSendMessage.getText().toString();
//        String sender = test.getString("UserNickname", null);
//
//        // 서버로 대화 전달
//        try {
//            url = "http://52.78.207.133:3000/messages/send/";
//            url += URLEncoder.encode(recipient, "utf-8")+"?";
//            url += "&body=" + URLEncoder.encode(message, "utf-8");
//            url += "&sender=" + URLEncoder.encode(sender, "utf-8");
//
//            enterSendMessage.setText("");
//            enterSendMessage.requestFocus(); // 전송 후 메세지 창 지우기
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("url", url);
//        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
//                url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String all_message = response.getString("msgs");
//                    // 우선 text창에 대화 전체 띄우기
//                    message_send.setText(all_message);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//        // queue에 Request를 추가해준다.
//        queue.add(request);
//    }


}