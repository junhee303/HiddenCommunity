//package com.example.junhe.hiddencommunity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//// 뷰페이저에 리사이클뷰 넣으려고 시도중..
//public class BoardBlankFragment extends FragmentActivity {
//    Context mContext;
//    RecyclerView recyclerView;
//    RecyclerView.Adapter Adapter;
//    RecyclerView.LayoutManager layoutManager;
//
//    public BoardBlankFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_board_blank_fragment);
//
//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        PagerAdapter pagerAdapter =
//                new PagerAdapter(getSupportFragmentManager(), BoardViewPagerActivity.this);
//
////        pagerAdapter.addFragment(new BoardBlankFragment(),"자유");
////        pagerAdapter.addFragment(new BoardBlankFragment(),"전공1");
////        pagerAdapter.addFragment(new BoardBlankFragment(),"전공2");
//
//        viewPager.setAdapter(pagerAdapter);
//
//        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//
//        // Iterate over all tabs and set the custom view
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }
//
//    }
//
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        // Inflate the layout for this fragment
////        View rootView = inflater.inflate(R.layout.activity_board_blank_fragment, container, false);
////
////        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view2);
////        recyclerView.setHasFixedSize(true);
////
////        // Item 리스트에 아이템 객체 넣기
////        ArrayList<BoardItem> items = new ArrayList<>();
////
////        String BoardId = "BoardId";
////        String Category = "자유";
////        String Title = "안녕하세요";
////        String Author = "조주니";
////        String Date = "2016-11-29";
////        String Body = "오늘 가입했어요~";
////        String Tag = "#가입 #인사";
////        int Hit = 3;
////        int Like = 1;
////        int Hate = 0;
////
////        System.out.println("댓글 내용: " + BoardId + " " + Category + " " + Title + " " + Author + " " + Date + " " + Body + " " + Tag + " " + Hit + " " + Like + " " + Hate);
////
////        items.add(new BoardItem(BoardId, Category, Title, Author, Date, Body, Tag, Hit, Like, Hate));
////        items.add(new BoardItem(BoardId, Category, Title, Author, Date, "두번째 글", Tag, Hit, Like, Hate));
////        items.add(new BoardItem(BoardId, Category, Title, Author, Date, "세번째 글", Tag, Hit, Like, Hate));
////        items.add(new BoardItem(BoardId, Category, Title, Author, Date, "네번째 글", Tag, Hit, Like, Hate));
////        items.add(new BoardItem(BoardId, Category, Title, Author, Date, "다섯번째 글", Tag, Hit, Like, Hate));
////
////
////
////        layoutManager = new LinearLayoutManager(getActivity());
////        recyclerView.setLayoutManager(layoutManager);
////
////        Adapter = new MyAdapter(items,mContext);
////        recyclerView.setAdapter(Adapter);
////
////        return rootView;
////    }
//
//    // MyAdapter
//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> //여기 이상..
//    {
//        private Context context;
//        private ArrayList<BoardItem> mItems;
//
//        // Allows to remember the last item shown on screen
//        private int lastPosition = -1;
//
//        public MyAdapter(ArrayList<BoardItem> items, Context mContext)
//        {
//            mItems = items;
//            context = mContext;
//        }
//
//        // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            // 새로운 뷰를 만든다
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_board,parent,false);
//            ViewHolder holder = new ViewHolder(v);
//            return holder;
//        }
//
//        // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//
//            holder.txtTitle.setText(mItems.get(position).Title);
//            holder.txtAuthor.setText(mItems.get(position).Author);
//            holder.txtDate.setText(mItems.get(position).Date);
//            holder.txtBody.setText(mItems.get(position).Body);
//            holder.txtTag.setText(mItems.get(position).Tag);
////            holder.txtHit.setText(mItems.get(position).Hit); //여기 이상..
////            holder.txtLike.setText(mItems.get(position).Like);
////            holder.txtHate.setText(mItems.get(position).Hate);
//
//            // txtBody 부분 텍스트 왼쪽에서 날아들어오는 애니메이션
//            setAnimation(holder.txtBody, position);
//        }
//
//        // // 필수로 Generate 되어야 하는 메소드 3
//        @Override
//        public int getItemCount() {
//            return mItems.size();
//        }
//
//        public class ViewHolder  extends RecyclerView.ViewHolder {
//
//            public TextView txtTitle;
//            public TextView txtAuthor;
//            public TextView txtDate;
//            public TextView txtBody;
//            public TextView txtTag;
//            public TextView txtHit;
//            public TextView txtLike;
//            public TextView txtHate; // 일단 댓글수 칸에 띄움
//
//            public ViewHolder(View view) {
//                super(view);
//                txtTitle = (TextView) view.findViewById(R.id.Title);
//                txtAuthor = (TextView) view.findViewById(R.id.Author);
//                txtDate = (TextView) view.findViewById(R.id.Date);
//                txtBody = (TextView) view.findViewById(R.id.Body);
//                txtTag = (TextView) view.findViewById(R.id.Tag);
//                txtHit = (TextView) view.findViewById(R.id.count_hit);
//                txtLike = (TextView) view.findViewById(R.id.count_like);
//                txtHate = (TextView) view.findViewById(R.id.count_comment);
//            }
//        }
//
//        private void setAnimation(View viewToAnimate, int position)
//        {
//            // 새로 보여지는 뷰라면 애니메이션을 해줍니다
//            if (position > lastPosition)
//            {
//                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//                viewToAnimate.startAnimation(animation);
//                lastPosition = position;
//            }
//        }
//
//    }
//
//}