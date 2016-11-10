package com.example.junhe.hiddencommunity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.CommentData;

public class BoardReadingActivity extends AppCompatActivity {

    private TextView select_board;
    private TextView TitleOfWriting;
    private TextView Writer;
    private TextView DateOfWriting;
    private TextView ContentOfWriting;
    private TextView TagOfWriting;
    private Button bLikeOn;
    private Button bLikeOff;
    private Button bAddComment;
    private TextView TheNumberOfLike;
    private boolean click_like = false;
    private int count_like = 0;
    private int count_comment = 0;

    final Context context = this;
    private TextView postedComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_reading);

        select_board = (TextView) findViewById(R.id.select_board);
        TitleOfWriting = (TextView) findViewById(R.id.TitleOfWriting);
        DateOfWriting = (TextView) findViewById(R.id.DateOfWriting);
        ContentOfWriting = (TextView) findViewById(R.id.ContentOfWriting);
        TagOfWriting = (TextView) findViewById(R.id.TagOfWriting);
        bLikeOn = (Button) findViewById(R.id.bLikeOn);
        bLikeOff = (Button) findViewById(R.id.bLikeOff);
        bAddComment = (Button) findViewById(R.id.bAddComment);
        TheNumberOfLike = (TextView) findViewById(R.id.TheNumberOfLike);
        postedComment = (TextView) findViewById(R.id.postedComment);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String content = extras.getString("content");
        String tag = extras.getString("tag");
        TitleOfWriting.setText(title);
        ContentOfWriting.setText(content);
        TagOfWriting.setText(tag);


        System.out.println("여기는 BoardReadingActivity로 들어온 거!! count_like는 " + count_like);
        System.out.println("click_like는 " + click_like);

        bLikeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (click_like == true) {
                    bLikeOn.setVisibility(View.INVISIBLE);
                    bLikeOff.setVisibility(View.VISIBLE);
                    click_like = false;
                }
                count_like--;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);
            }
        });

        bLikeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (click_like == false) {
                    bLikeOn.setVisibility(View.VISIBLE);
                    bLikeOff.setVisibility(View.INVISIBLE);
                    click_like = true;
                }
                count_like++;
                TheNumberOfLike.setText("좋아요 수 = " + count_like + "click_like 상태는" + click_like);

            }
        });

        bAddComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //get comment_post_popup.wml view
                LayoutInflater li = LayoutInflater.from(context);
                View commentPopupView = li.inflate(R.layout.comment_post_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //set comment_post_popup.wml to alertdialog builder
                alertDialogBuilder.setView(commentPopupView);

                final EditText userInput = (EditText) commentPopupView.findViewById(R.id.editTextDialogComment);

                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        postedComment.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }
}