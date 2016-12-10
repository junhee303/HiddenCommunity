package com.example.junhe.hiddencommunity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatMainActivity extends AppCompatActivity {
    private ChatMessageAdapter chatMessageAdapter;
    private EditText etMsg;
    private Button buttonSend;
    private Button move_chatList_btn;
    Intent intent;
    private boolean side = false;

    private String Author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        move_chatList_btn = (Button) findViewById(R.id.move_chatList_btn);
        TextView otherNickname = (TextView) findViewById(R.id.toolbar_textview);
        buttonSend = (Button) findViewById(R.id.send_btn);
        etMsg = (EditText) findViewById(R.id.chatText);
        // message_send = (TextView) findViewById(R.id.message_send);

//        Bundle extras = getIntent().getExtras();
//        Author = extras.getString("Author");
//        otherNickname.setText(Author);

        chatMessageAdapter = new ChatMessageAdapter(this.getApplicationContext(), R.layout.chatting_message);

        final ListView listView = (ListView) findViewById(R.id.listView);
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

        // 전송 버튼 클릭
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etMsg.getText().toString().length() == 0) {
                    Toast.makeText(ChatMainActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.chat_menu, menu);
//
//
//        return true;
//    }

    // 메세지 내용 전송
    public void sendChatMessage() {

        String strMsg = etMsg.getText().toString();
        System.out.println("전송할 메세지 내용 : " + strMsg); // ok
        chatMessageAdapter.add(new ChatMessage(side, strMsg)); // 내가 보낸 메세지는 오른쪽
        System.out.println(chatMessageAdapter.getCount());

        side = !side;
        System.out.println("메세지 왼쪽 true 오른쪽 false : " + side);
    }

    // 상단의 [ < ] 아이콘 클릭 시
    public void pushBoardListButton() {
        move_chatList_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 상단바의 [ < ] 버튼 클릭 시 게시글 목록으로 이동
                Toast.makeText(ChatMainActivity.this, "대화하기 목록으로 이동", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(ChatMainActivity.this, ChatListActivity.class);
//                startActivityForResult(intent, 1000);
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