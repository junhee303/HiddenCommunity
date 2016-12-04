//package com.example.junhe.hiddencommunity;
//
//import android.content.Context;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by junhe on 2016-11-10.
// */
//public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.MyViewHolder> {
//
//    private ArrayList<String> mBoardId;
//    private ArrayList<String> mAuthorSet;
//    private ArrayList<String> mDateSet;
//    private ArrayList<String> mBodySet;
//
//    public static CardView mCardView;
//    public static TextView txtAuthor;
//    public static TextView txtDate;
//    public static TextView txtBody;
//
//    Context mContext;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//
//        public MyViewHolder(View v) {
//            super(v);
//
//            mCardView = (CardView) v.findViewById(R.id.comment_list);
//            txtAuthor = (TextView) v.findViewById(R.id.comment_Author);
//            txtDate = (TextView) v.findViewById(R.id.comment_Date);
//            txtBody = (TextView) v.findViewById(R.id.comment_Body);
//
//        }
//    }
//    public static String getBoardId(){
//        return "boardId";
//    }
//
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public Comment_Adapter(Context mContext, ArrayList<String> mBoardId, ArrayList<String> mAuthorSet, ArrayList<String> mDateSet, ArrayList<String> mBodySet) {
//
//        this.mContext = mContext;
//        this.mBoardId = mBoardId;
//        this.mAuthorSet = mAuthorSet;
//        this.mDateSet = mDateSet;
//        this.mBodySet = mBodySet;
//    }
//
//    public void update(){
//        notifyDataSetChanged();
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public Comment_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                         int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_comment_item, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        txtAuthor.setText(mAuthorSet.get(position));
//        txtDate.setText(mDateSet.get(position));
//        txtBody.setText(mBodySet.get(position));
//        System.out.println("여기서 댓글화면 그려져야하는거 아닌가");
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return mAuthorSet.size();
//    }
//
//}
//
//
//
//
//
//
////    private CommentData mCommentData;
////
////
////    public Comment_Adapter(CommentData mCommentData){
////
////        this.mCommentData = mCommentData;
////    }
////    public void notifyDataSetChanged(){
////
////    };
////
////
////}
