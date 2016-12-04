package com.example.junhe.hiddencommunity;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
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
import com.example.junhe.hiddencommunity.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChatMainActivity extends AppCompatActivity {
    ChatMessageAdapter chatMessageAdapter;

    TextView otherNickname;
    EditText enterSendMessage;
    Button sendButton;
    TextView message_send;

    String url, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        otherNickname = (TextView) findViewById(R.id.toolbar_textview);
        enterSendMessage = (EditText) findViewById(R.id.sendMessage);
        sendButton = (Button) findViewById(R.id.send_btn);
        message_send = (TextView) findViewById(R.id.message_send);


        Bundle extras = getIntent().getExtras();
        String Author = extras.getString("Author");
        otherNickname.setText(Author);
        System.out.println("대화하는 상대방 닉네임은 : " + Author);

        clickSendButton(); // 전송 버튼 클릭

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_menu, menu);

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
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.setting_btn:
//
//                Toast.makeText(ChatMainActivity.this, "입력한 메세지를 전송합니다", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//
//    }

    // 전송 버튼 클릭
    public void clickSendButton() {

        enterSendMessage = (EditText) findViewById(R.id.sendMessage);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (enterSendMessage.getText().toString().length() == 0) {
                    Toast.makeText(ChatMainActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    enterSendMessage.requestFocus();
                    return;
                }

                sendMessage(); // 대화 읽어오기

            }

        });
    }

    // 대화 읽어오기
    public void sendMessage() {
        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();


        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String recipient = otherNickname.getText().toString();
        String message = enterSendMessage.getText().toString();
        String sender = test.getString("UserNickname", null);

        // 서버로 대화 전달
        try {
            url = "http://52.78.207.133:3000/messages/send/";
            url += URLEncoder.encode(recipient, "utf-8")+"?";
            url += "&body=" + URLEncoder.encode(message, "utf-8");
            url += "&sender=" + URLEncoder.encode(sender, "utf-8");

            enterSendMessage.setText("");
            enterSendMessage.requestFocus(); // 전송 후 메세지 창 지우기
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("url", url);
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String all_message = response.getString("msgs");
                    // 우선 text창에 대화 전체 띄우기
                    message_send.setText(all_message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

//    public void send(View view){
//        EditText etMsg = (EditText)findViewById(R.id.chatText);
//        String strMsg = (String)etMsg.getText().toString();
//        //chatMessageAdapter.add(new ChatMessage(strMsg));
//    }
}