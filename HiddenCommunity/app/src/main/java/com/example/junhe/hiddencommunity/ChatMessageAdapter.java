package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by terry on 2015. 10. 7..
 */
public class ChatMessageAdapter extends ArrayAdapter {

    ArrayList<ChatMessage> msgs = new ArrayList();
    //boolean message_left = true;
    private LinearLayout chatMessageContainer;

    public ChatMessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //@Override
    public void add(ChatMessage object){
        msgs.add(object);
        System.out.println("ChatMessageAdapter에 채팅 메세지 ArrayList add");
        super.add(object);
    }


    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public ChatMessage getItem(int index) {
        return (ChatMessage)msgs.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        System.out.println("ChatMessageAdapter의 getView 부분");
        if (row == null) {
            // inflator를 생성하여, chatting_message.xml을 읽어서 View객체로 생성한다.
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chatting_message, parent, false);
        }
        chatMessageContainer = (LinearLayout)row.findViewById(R.id.chatmessage_container);
        // Array List에 들어 있는 채팅 문자열을 읽어
        ChatMessage msg = msgs.get(position);
        // Inflater를 이용해서 생성한 View에, ChatMessage를 삽입한다.
        TextView msgText = (TextView) row.findViewById(R.id.chatmessage);
        TextView msgDate = (TextView) row.findViewById(R.id.Date);
        msgText.setText(msg.getMessage());
        msgDate.setText(msg.getDate());
        System.out.println("msgText.setText는 : " + msg.getMessage());


        // 9 패치 이미지로 채팅 버블을 출력
        if(msg.getSide() == false) {// 나의 메세지 - 오른쪽 배치
            msgText.setBackground(this.getContext().getResources().getDrawable(R.drawable.bubble_a));
            chatMessageContainer.setGravity(Gravity.END);
        } else if(msg.getSide() == true) { // 상대방의 메세지 - 왼쪽 배치
            msgText.setBackground(this.getContext().getResources().getDrawable(R.drawable.bubble_b));
            chatMessageContainer.setGravity(Gravity.START);
        }

        return row;

    }

    public void update() {
        notifyDataSetChanged();
    }
}