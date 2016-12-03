package com.example.junhe.hiddencommunity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BoardBlankFragment extends Fragment {

    public BoardBlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.board_blank_fragment, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        String[] mTitleSet = {"제목1", "제목2", "제목3", "제목4", "제목5"};
        String[] mAuthorSet = {"작성자1", "작성자2", "작성자3", "작성자4", "작성자5"};
        String[] mDateSet = {"날짜1", "날짜2", "날짜3", "날짜4", "날짜5"};
        String[] mBodySet = {"글 내용이 길어지면 cardview 길이도 늘어나야 함", "내용", "내용", "내용", "내용"};
        String[] mTagSet = {"#태그", "#태그", "#태그", "#태그", "#태그"};
        String[] mHitSet = {"1", "2", "3", "4", "5"};
        String[] mLikeSet = {"1", "2", "3", "4", "5"};
        String[] mCommentSet = {"1", "2", "3", "4", "5"};

        Board_Adapter adapter = new Board_Adapter(mTitleSet, mAuthorSet, mDateSet, mBodySet, mTagSet, mHitSet, mLikeSet, mCommentSet);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}
