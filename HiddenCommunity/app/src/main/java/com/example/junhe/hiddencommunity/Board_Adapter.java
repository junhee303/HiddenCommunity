package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by junhe on 2016-12-01.
 */

public class Board_Adapter extends RecyclerView.Adapter<Board_Adapter.MyViewHolder> {
    private ArrayList<String> mCategory;
    private ArrayList<String> mBoardId;
    private ArrayList<String> mTitleSet;
    private ArrayList<String> mAuthorSet;
    private ArrayList<String> mDateSet;
    private ArrayList<String> mBodySet;
    private ArrayList<String> mTagSet;
    private ArrayList<Integer> mHitSet;
    private ArrayList<Integer> mLikeSet;
    private ArrayList<Integer> mCommentSet;
    private String url;

    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView txtTitle;
        public TextView txtAuthor;
        public TextView txtDate;
        public TextView txtBody;
        public TextView txtTag;
        public TextView txtHit;
        public TextView txtLike;
        public TextView txtComment;


        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            txtTitle = (TextView) v.findViewById(R.id.Title);
            txtAuthor = (TextView) v.findViewById(R.id.Author);
            txtDate = (TextView) v.findViewById(R.id.Date);
            txtBody = (TextView) v.findViewById(R.id.Body);
            txtTag = (TextView) v.findViewById(R.id.Tag);
            txtHit = (TextView) v.findViewById(R.id.count_hit);
            txtLike = (TextView) v.findViewById(R.id.count_like);
            txtComment = (TextView) v.findViewById(R.id.count_comment);
        }
    }

    public static String getBoardId() {
        return "boardId";
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public Board_Adapter(Context context, ArrayList<String> Category, ArrayList<String> BoardId, ArrayList<String> TitleSet, ArrayList<String> AuthorSet, ArrayList<String> DateSet, ArrayList<String> BodySet, ArrayList<String> TagSet, ArrayList<Integer> HitSet, ArrayList<Integer> LikeSet, ArrayList<Integer> CommentSet) {

        this.mContext = context;
        this.mCategory = Category;
        this.mBoardId = BoardId;
        this.mTitleSet = TitleSet;
        this.mAuthorSet = AuthorSet;
        this.mDateSet = DateSet;
        this.mBodySet = BodySet;
        this.mTagSet = TagSet;
        this.mHitSet = HitSet;
        this.mLikeSet = LikeSet;
        this.mCommentSet = CommentSet;
    }

    public void update() {
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Board_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtTitle.setText(mTitleSet.get(position));
        holder.txtAuthor.setText(mAuthorSet.get(position));
        holder.txtDate.setText(mDateSet.get(position));
        holder.txtBody.setText(mBodySet.get(position));
        holder.txtTag.setText(mTagSet.get(position));
        holder.txtHit.setText("조회  " + mHitSet.get(position));
        holder.txtLike.setText("좋아요  " + mLikeSet.get(position));
        holder.txtComment.setText("댓글  " + mCommentSet.get(position));


        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이템 클릭 시 해당 게시물의 boardId를 서버에 요청
                String boardId = mBoardId.get(position);

                // 서버로 게시글 boardId 전달
                try {
                    url = "http://52.78.207.133:3000/boards/read/";
                    url += "boardId=" + URLEncoder.encode(boardId, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("url", url);

                Intent intent = new Intent(mContext, BoardReadingActivity.class);
                intent.putExtra("boardId", boardId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mTitleSet.size();
    }

}