package com.example.junhe.hiddencommunity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by junhe on 2016-12-01.
 */

public class Board_Adapter  extends RecyclerView.Adapter<Board_Adapter.MyViewHolder> {
    private String[] mTitleSet;
    private String[] mAuthorSet;
    private String[] mDateSet;
    private String[] mBodySet;
    private String[] mTagSet;
    private String[] mHitSet;
    private String[] mLikeSet;
    private String[] mCommentSet;

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
        public TextView txtCommentSet;

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
            txtCommentSet = (TextView) v.findViewById(R.id.count_comment);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Board_Adapter(String[] mTitleSet, String[] mAuthorSet, String[] mDateSet, String[] mBodySet, String[] mTagSet, String[] mHitSet, String[] mLikeSet, String[] mCommentSet) {

        this.mTitleSet = mTitleSet;
        this.mAuthorSet = mAuthorSet;
        this.mDateSet = mDateSet;
        this.mBodySet = mBodySet;
        this.mTagSet = mTagSet;
        this.mHitSet = mHitSet;
        this.mLikeSet = mLikeSet;
        this.mCommentSet = mCommentSet;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtTitle.setText(mTitleSet[position]);
        holder.txtAuthor.setText(mAuthorSet[position]);
        holder.txtDate.setText(mDateSet[position]);
        holder.txtBody.setText(mBodySet[position]);
        holder.txtTag.setText(mTagSet[position]);
        holder.txtHit.setText("조회  " + mHitSet[position]);
        holder.txtLike.setText("좋아요  " + mLikeSet[position]);
        holder.txtCommentSet.setText("댓글  " + mCommentSet[position]);
    }

    @Override
    public int getItemCount() {

        return mTitleSet.length;
    }
}