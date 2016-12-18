package com.example.junhe.hiddencommunity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.List;

import data.BoardData;

public class BoardBlankFragment extends Fragment {

    private Spinner BoardRangeSpinner;

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
    String mCategory_name;
    int mParam1;
    int range_position;

    public BoardBlankFragment() {
        // Required empty public constructor
    }

    //    public void func(int position){
//        Log.d("range",""+position);
//        range_position = position;
//        System.out.println("category는 " + mCategory_name + " / position은 " + range_position);
//
//        sendRequest_boardList(mCategory_name); // 게시글 목록 받아오기
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getInt("index");
        } else {
            mParam1 = 0;
        }
        int board_position = mParam1;
        mCategory_name = ((BoardRecyclerViewActivity) getActivity()).tabTitles.get(board_position);
        //System.out.println("OnCreate Method : board_position은 " + board_position + "/ category는 " + mCategory_name);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //화면에 실제로 보일때
//        } else {
//            //preload 될때(전페이지에 있을때)
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(mCategory_name + ": onCreateView Method invoked!!!!!!!!!!!!! position : " + range_position);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.board_blank_fragment, container, false);
        BoardRangeSpinner = (Spinner) rootView.findViewById(R.id.board_range_spinner);

        selectBoardRange(); // 게시판 정렬 선택

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        mAdapter = new Board_Adapter(getActivity(), mCategory, mBoardId, mTitleSet, mAuthorSet, mDateSet, mBodySet, mTagSet, mHitSet, mLikeSet, mCommentSet);
        rv.setAdapter(mAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

       // sendRequest_boardList(mCategory_name); // 게시글 목록 받아오기

        return rootView;
    }

    // 게시판 정렬 선택
    public void selectBoardRange() {
        BoardRangeSpinner.setOnItemSelectedListener(mOnItemSelectedListener);

        List<String> list = new ArrayList<>();
        list.add("최신순"); // position 0
        list.add("조회순"); // position 1
        list.add("좋아요순"); // position 2

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BoardRangeSpinner.setAdapter(dataAdapter);

    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d("tag", "onItemSelected() entered!!");
            range_position = BoardRangeSpinner.getSelectedItemPosition();
            Log.d("tag", "선택한 정렬의 list position은  = " + range_position);
            // 최신순 : 0 / 조회순 : 1 / 좋아요순 : 2

           sendRequest_boardList(mCategory_name); // 게시글 목록 받아오기

//            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + mViewPager.getCurrentItem());
//            // based on the current position you can then cast the page to the correct
//            // class and call the method:
//            if (mViewPager.getCurrentItem() == 0 && page != null) {
//                ((BoardBlankFragment)page).func(range_position);
//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.d("tag", "onNothingSelected() entered!!");
        }

    };

    // 게시글 목록 받아오기 - JsonObject
    public void sendRequest_boardList(String _Caterogy) {

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


        VolleySingleton v = VolleySingleton.getInstance();
        RequestQueue queue = v.getRequestQueue();

        //final String category = _Caterogy;
        //System.out.println("2해당 게시글의 카테고리는 " + category);

        int range = range_position; // 선택한 정렬
        //System.out.println("선택한 정렬의 ranges는  = " + range);

        // 서버로 카테고리 전달
        try {
            String url_category = "http://52.78.207.133:3000/boards/list/";
            url_category += URLEncoder.encode(mCategory_name, "utf-8") + "/";
            url_category += range;
            Log.d("url", url_category);

            CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,
                    url_category, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    System.out.println("sendRequest의 onResponse 부분");
                    JsonParser js = new JsonParser();

                    String Major1 = ((BoardRecyclerViewActivity) getActivity()).Major1;
                    String Major2 = ((BoardRecyclerViewActivity) getActivity()).Major2;
                    String Major3 = ((BoardRecyclerViewActivity) getActivity()).Major3;

                    if (mCategory_name.equals("자유")) {
                        ArrayList<BoardData> data_freeBoard = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_freeBoard에 넣기");
                        System.out.println(data_freeBoard.size());

                        for (int i = 0; i < data_freeBoard.size(); i++) {
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

                    } else if (mCategory_name.equals(Major1)) {
                        ArrayList<BoardData> data_major1 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major1에 넣기");
                        System.out.println(data_major1.size());

                        for (int i = 0; i < data_major1.size(); i++) {
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

                    } else if (mCategory_name.equals(Major2)) {
                        ArrayList<BoardData> data_major2 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major2에 넣기");
                        System.out.println(data_major2.size());

                        for (int i = 0; i < data_major2.size(); i++) {
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

                    } else if (mCategory_name.equals(Major3)) {
                        ArrayList<BoardData> data_major3 = js.getBoardData(response);

                        System.out.println("JsonParser의 response 받아서 data_major3에 넣기");
                        System.out.println(data_major3.size());

                        for (int i = 0; i < data_major3.size(); i++) {
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
