package com.example.junhe.hiddencommunity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatMainActivity extends AppCompatActivity {
    ChatMessageAdapter chatMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_menu, menu);

        chatMessageAdapter = new ChatMessageAdapter(this.getApplicationContext(),R.layout.chatting_message);
        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(chatMessageAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // 이게 필수

        // When message is added, it makes listview to scroll last message
        chatMessageAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatMessageAdapter.getCount()-1);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = null;

        switch (item.getItemId()) {
            case R.id.notice_btn:
                // 상단바의 알림 버튼 클릭 시 대화방 알림 on/off 설정
                text = "click the notice button";
                break;
            case R.id.setting_btn:
                //
                text = "click the setting button";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        return true;

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }

    public void send(View view){
        EditText etMsg = (EditText)findViewById(R.id.chatText);
        String strMsg = (String)etMsg.getText().toString();
        //chatMessageAdapter.add(new ChatMessage(strMsg));
    }
}