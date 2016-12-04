package com.example.junhe.hiddencommunity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import data.BoardData;

public class BoardBlankFragment extends Fragment {
    ArrayList<String> mBoardId = new ArrayList<>();
    ArrayList<String> mCategory = new ArrayList<>();
    ArrayList<String> mTitleSet = new ArrayList<>();
    ArrayList<String> mAuthorSet = new ArrayList<>();
    ArrayList<String> mDateSet = new ArrayList<>();
    ArrayList<String> mBodySet = new ArrayList<>();
    ArrayList<String> mTagSet = new ArrayList<>();
    ArrayList<Integer> mHitSet = new ArrayList<>();
    ArrayList<Integer> mLikeSet = new ArrayList<>();
    ArrayList<Integer> mCommentSet = new ArrayList<>();
    Board_Adapter mAdapter;
    int mParam1;
    int range_position;

    public BoardBlankFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getInt("index");
            //range_position = getArguments().getInt("range_position");
        }else{
            mParam1 = 0;
            //range_position = 4;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            //화면에 실제로 보일때
        }
        else
        {
            //preload 될때(전페이지에 있을때)
        }
    }

//    public BoardBlankFragment newInstance(String category) {
//        BoardBlankFragment fragment = new BoardBlankFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("category", category);
//        System.out.println("1해당 게시글의 카테고리는 " + args.get("category"));
//
//        sendRequest_boardList(args.get("category").toString()); // 게시글 목록 받아오기
//
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.board_blank_fragment, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        System.out.println("onCreatView 좀 들어와주라..");

        //int board_position = ((BoardRecyclerViewActivity)getActivity()).board_position;
        int board_position = mParam1;
        String category = ((BoardRecyclerViewActivity)getActivity()).tabTitles.get(board_position);

        System.out.println("☆☆☆board_position은 " + board_position + "/ category는 " + category);

        mBoardId.clear();
        mTitleSet.clear();
        mCategory.clear();
        mAuthorSet.clear();
        mDateSet.clear();
        mBodySet.clear();
        mTagSet.clear();
        mHitSet.clear();
        mLikeSet.clear();
        mCommentSet.clear();



        mAdapter = new Board_Adapter(getActivity(), mCategory, mBoardId, mTitleSet, mAuthorSet, mDateSet, mBodySet, mTagSet, mHitSet, mLikeSet, mCommentSet);

        rv.setAdapter(mAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        sendRequest_boardList(category); // 게시글 목록 받아오기

        return rootView;
    }



    // 게시글 목록 받아오기 - JsonObject
    public void sendRequest_boardList(String _Caterogy) {

        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        final String category = _Caterogy;
        System.out.println("2해당 게시글의 카테고리는 " + category);

        int range = ((BoardRecyclerViewActivity)getActivity()).range_position;
        // 서버로 카테고리 전달
        try {
            String url_category = "http://52.78.207.133:3000/boards/list/";
            url_category += URLEncoder.encode(category, "utf-8")+ "/";
            url_category += range;
            Log.d("url", url_category);

            CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                    url_category, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    System.out.println("sendRequest의 onResponse 부분");
                    JsonParser js = new JsonParser();

                    String Major1 = ((BoardRecyclerViewActivity)getActivity()).Major1;
                    String Major2 = ((BoardRecyclerViewActivity)getActivity()).Major2;
                    String Major3 = ((BoardRecyclerViewActivity)getActivity()).Major3;

                    if(category.equals("자유")) {
                        ArrayList<BoardData> data_freeBoard = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_freeBoard에 넣기");
                        System.out.println(data_freeBoard.size());

                        for(int i = 0; i <data_freeBoard.size(); i++) {
                            System.out.println("data.get(i).getBoardId() : " + data_freeBoard.get(i).getBoardId());
                            mBoardId.add(data_freeBoard.get(i).getBoardId());
                            mCategory.add(data_freeBoard.get(i).getCategory());
                            mTitleSet.add(data_freeBoard.get(i).getTitle());
                            mAuthorSet.add(data_freeBoard.get(i).getAuthor());
                            mDateSet.add(data_freeBoard.get(i).getDate());
                            mBodySet.add(data_freeBoard.get(i).getBody());
                            mTagSet.add(data_freeBoard.get(i).getTag());
                            mHitSet.add(data_freeBoard.get(i).getHit());
                            mLikeSet.add(data_freeBoard.get(i).getLike());
                            mCommentSet.add(data_freeBoard.get(i).getComment());
                        }

                        System.out.println("BoardData에서 받아와서 mAuthorSet에 add : " + mAuthorSet);

                    } else if (category.equals(Major1)) {
                        ArrayList<BoardData> data_major1 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major1에 넣기");
                        System.out.println(data_major1.size());

                        for(int i = 0; i <data_major1.size(); i++) {
                            System.out.println("data.get(i).getBoardId() : " + data_major1.get(i).getBoardId());
                            mBoardId.add(data_major1.get(i).getBoardId());
                            mCategory.add(data_major1.get(i).getCategory());
                            mTitleSet.add(data_major1.get(i).getTitle());
                            mAuthorSet.add(data_major1.get(i).getAuthor());
                            mDateSet.add(data_major1.get(i).getDate());
                            mBodySet.add(data_major1.get(i).getBody());
                            mTagSet.add(data_major1.get(i).getTag());
                            mHitSet.add(data_major1.get(i).getHit());
                            mLikeSet.add(data_major1.get(i).getLike());
                            mCommentSet.add(data_major1.get(i).getComment());
                        }

                        System.out.println("BoardData에서 받아와서 mAuthorSet에 add : " + mAuthorSet);

                    } else if (category.equals(Major2)) {
                        ArrayList<BoardData> data_major2 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major2에 넣기");
                        System.out.println(data_major2.size());

                        for(int i = 0; i <data_major2.size(); i++) {
                            System.out.println("data.get(i).getBoardId() : " + data_major2.get(i).getBoardId());
                            mBoardId.add(data_major2.get(i).getBoardId());
                            mCategory.add(data_major2.get(i).getCategory());
                            mTitleSet.add(data_major2.get(i).getTitle());
                            mAuthorSet.add(data_major2.get(i).getAuthor());
                            mDateSet.add(data_major2.get(i).getDate());
                            mBodySet.add(data_major2.get(i).getBody());
                            mTagSet.add(data_major2.get(i).getTag());
                            mHitSet.add(data_major2.get(i).getHit());
                            mLikeSet.add(data_major2.get(i).getLike());
                            mCommentSet.add(data_major2.get(i).getComment());
                        }

                        System.out.println("BoardData에서 받아와서 mAuthorSet에 add : " + mAuthorSet);

                    } else if (category.equals(Major3)) {
                        ArrayList<BoardData> data_major3 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major3에 넣기");
                        System.out.println(data_major3.size());

                        for(int i = 0; i <data_major3.size(); i++) {
                            System.out.println("data.get(i).getBoardId() : " + data_major3.get(i).getBoardId());
                            mBoardId.add(data_major3.get(i).getBoardId());
                            mCategory.add(data_major3.get(i).getCategory());
                            mTitleSet.add(data_major3.get(i).getTitle());
                            mAuthorSet.add(data_major3.get(i).getAuthor());
                            mDateSet.add(data_major3.get(i).getDate());
                            mBodySet.add(data_major3.get(i).getBody());
                            mTagSet.add(data_major3.get(i).getTag());
                            mHitSet.add(data_major3.get(i).getHit());
                            mLikeSet.add(data_major3.get(i).getLike());
                            mCommentSet.add(data_major3.get(i).getComment());
                        }

                    }
                    mAdapter.update();

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
            // queue에 Request를 추가해준다.
            queue.add(request);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
