//package com.example.junhe.hiddencommunity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.TextView;
//// 뷰페이저에 리사이클뷰 넣으려고 시도중..
//public class BoardViewPagerActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_board_blank_fragment);
//
////        Toolbar toolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
////        setSupportActionBar(toolbar);
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
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.writing_btn) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    class PagerAdapter extends FragmentPagerAdapter {
//
//        String tabTitles[] = new String[]{"Tab One", "Tab Two", "Tab Three"};
//        Context context;
//
//        public PagerAdapter(FragmentManager fm, Context context) {
//            super(fm);
//            this.context = context;
//            System.out.println("여기서 에러나나?");
//        }
////
////        public void addFragment(Fragment fragment, String title) {
////            mFragments.add(fragment);
////            mFragmentTitles.add(title);
////        }
//
//        @Override
//        public int getCount() {
//            return tabTitles.length;
//        }
//
//        @Override
//        public FragmentActivity getItem(int position) {
//            System.out.println("여기서 에러나나? x");
//            switch (position) {
//                case 0:
//                    return new BoardBlankFragment();
//                case 1:
//                    return new BoardBlankFragment();
//                case 2:
//                    return new BoardBlankFragment();
//            }
//
//            return null;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Generate title based on item position
//            return tabTitles[position];
//        }
//
//        public View getTabView(int position) {
//            View tab = LayoutInflater.from(BoardViewPagerActivity.this).inflate(R.layout.list_board, null);
//            TextView txtTitle = (TextView) tab.findViewById(R.id.Title);
//            TextView txtAuthor = (TextView) tab.findViewById(R.id.Author);
//            TextView txtDate = (TextView) tab.findViewById(R.id.Date);
//            TextView txtBody = (TextView) tab.findViewById(R.id.Body);
//            TextView txtTag = (TextView) tab.findViewById(R.id.Tag);
//
//            txtTitle.setText(tabTitles[position]);
//            txtAuthor.setText(tabTitles[position]);
//            txtDate.setText(tabTitles[position]);
//            txtBody.setText(tabTitles[position]);
//            txtTag.setText(tabTitles[position]);
//
//            return tab;
//        }
//
//    }
//}