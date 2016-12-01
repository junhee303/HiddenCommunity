package com.example.junhe.hiddencommunity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recycler_view);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String Major1 = test.getString("UserMajor1", "");
        String Major2 = test.getString("UserMajor2", "");
        String Major3 = test.getString("UserMajor3", "");

        ArrayList<String> tabTitles= new ArrayList<>();


        tabTitles.add("자유");
        tabTitles.add(Major1);
        if(Major2 != "") {
            tabTitles.add(Major2);
        }
        if(Major3 != "") {
            tabTitles.add(Major3);
        }

        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), BoardRecyclerViewActivity.this, tabTitles);

        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    class PagerAdapter extends FragmentPagerAdapter {

        ArrayList<String> tabTitles= new ArrayList<>();
        Context context;

        public PagerAdapter(FragmentManager fm, Context context, ArrayList<String> tabTitles) {
            super(fm);
            this.context = context;
            this.tabTitles = tabTitles;
        }


//        public void setTabTitle(ArrayList<String> tabTitles) {
//            this.tabTitles = tabTitles;
//
//        }
        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new BoardBlankFragment();
                case 1:
                    return new BoardBlankFragment();
                case 2:
                    return new BoardBlankFragment();
                case 3:
                    return new BoardBlankFragment();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position);
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(BoardRecyclerViewActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }
    }
}