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

public class MessageRoomActivity extends AppCompatActivity {
    private CustomJsonRequest request_message;
    private MessageAdapter messageAdapter;
    private TextView date;
    private EditText etMsg;
    private Button buttonSend;
    private Button move_messageList_btn;
    Intent intent;

    private String recipient;
    String myNickname; // 내 닉네임

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        move_messageList_btn = (Button) findViewById(R.id.move_messageList_btn);
        TextView otherNickname = (TextView) findViewById(R.id.toolbar_textview);
        date = (TextView) findViewById(R.id.Date);
        buttonSend = (Button) findViewById(R.id.send_btn);
        etMsg = (EditText) findViewById(R.id.messageText);

        Bundle extras = getIntent().getExtras();
        recipient = extras.getString("Author");
        otherNickname.setText(recipient);

        sendRequest_sumMessageData(); // 채팅방 입장시 이전 채팅 읽어오기
        pushBoardListButton(); // 상단의 [ < ] 아이콘 클릭 시

        // 전송 버튼 클릭
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etMsg.getText().toString().length() == 0) {
                    Toast.makeText(MessageRoomActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    etMsg.requestFocus();
                    return;
                } else {
                    sendMessage(); // 메세지 내용 전송
                    etMsg.setText("");
                    etMsg.requestFocus();
                    return;
                }
            }
        });

    }

    // 채팅방 입장시 이전 채팅 읽어오기
    public void sendRequest_sumMessageData() {
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        myNickname = test.getString("UserNickname", null);

        System.out.println("채팅방 입장시 이전 채팅 읽어오기");

        // 서버로 대화 전달
        try {
            String url = "http://52.78.207.133:3000/messages/room/";
            url += URLEncoder.encode(recipient, "utf-8");
            url += "/" + URLEncoder.encode(myNickname, "utf-8");

            Log.d("url", url);
            sendRequest_message(url); // 서버에서 채팅 내용 읽어오기

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 메세지 내용 전송
    public void sendMessage() {
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
            sendRequest_message(url); // 서버에서 채팅 내용 읽어오기

            etMsg.setText("");
            etMsg.requestFocus(); // 전송 후 메세지 창 비우기
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //  서버에서 채팅 내용 읽어오기
    public void sendRequest_message(String url) {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        System.out.println("sendRequest_message 내부");

        request_message = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("sendRequest_message onResponse 부분");
                JsonParser js = new JsonParser();
                ArrayList<Message> message = js.getMessage(response, myNickname);
                System.out.println("JsonParser의 response 받아서 message에 넣기");
                int message_count = message.size();
                System.out.println("해당 채팅방 메세지 갯수인 message_count " + message_count);

                messageAdapter = new MessageAdapter(getApplicationContext(), R.layout.message);

                for(int i = 0; i < message_count; i++){
                    messageAdapter.add(message.get(i));
                }

                final ListView listView = (ListView) findViewById(R.id.listView);
                listView.setDivider(null);
                listView.setAdapter(messageAdapter);
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // 이게 필수
                // When message is added, it makes listview to scroll last message
                messageAdapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listView.setSelection(messageAdapter.getCount() - 1);
                    }
                });

                messageAdapter.update();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // queue에 Request를 추가해준다.
        queue.add(request_message);
    }

    // 상단의 [ < ] 아이콘 클릭 시
    public void pushBoardListButton() {
        move_messageList_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 [ < ] 버튼 클릭 시 게시글 목록으로 이동
                Toast.makeText(MessageRoomActivity.this, "대화하기 목록으로 이동", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MessageRoomActivity.this, MessageListActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

}