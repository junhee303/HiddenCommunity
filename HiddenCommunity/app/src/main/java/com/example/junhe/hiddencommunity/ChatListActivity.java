//package com.example.junhe.hiddencommunity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import data.MajorData;
//
///**
// * Created by junhe on 2016-12-11.
// */
//
//public class ChatListActivity extends AppCompatActivity {
//    private ArrayList<ChatMessage> chat_message = new ArrayList();
//    Context mContext = this;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_home);
//
//        // ListView 가져오기
//        final ListView chatListView = (ListView) findViewById(R.id.chatListView);
//
//        ChatListAdapter adapter = new ChatListAdapter(mContext, 0, chat_message);
//        // ListView에 각각의 전공표시를 제어하는 Adapter를 설정
//        chat_message.setAdapter(adapter);
//
//        // 전공 클릭시 이벤트 리스너 등록
//        chat_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // TODO 아이템 클릭시에 구현할 내용은 여기에.
//                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//
//                // 전공 선택시 RegisterActivity의 etMajor에 전달
//                String selected_major = chat_message.get(position).getMajor();
//                intent.putExtra("selected_major", selected_major);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//    }
//
//    private class ChatListAdapter extends ArrayAdapter<ChatMessage> {
//
//        private ArrayList<ChatMessage> mChatMessage;
//
//        public ChatListAdapter(Context context, int resource, ArrayList<ChatMessage> chatMessage) {
//            super(context, resource, chatMessage);
//
//            mChatMessage = chatMessage;
//        }
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//            View rowView = inflater.inflate(R.layout.list_major, null, true);
//
//            TextView txtMajor = (TextView) rowView.findViewById(R.id.Major);
//            TextView txtSubMajor = (TextView) rowView.findViewById(R.id.SubMajor);
//
//            txtMajor.setText(mChatMessage.get(position).getSender());
//
//            return rowView;
//        }
//    }
//}
