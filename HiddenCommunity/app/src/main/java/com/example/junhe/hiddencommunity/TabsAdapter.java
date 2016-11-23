//package com.example.junhe.hiddencommunity;
//
//import android.app.ActionBar;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//
//import java.util.ArrayList;
//
///**
// * Created by junhe on 2016-11-22.
// */
//
//public class TabsAdapter extends FragmentPagerAdapter implements android.app.ActionBar.TabListener, ViewPager.OnPageChangeListener{
//    private final Context mContext;
//    private final ActionBar mActionBar;
//    private final ViewPager mViewPager;
//    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
//    private final String TAG = "";
//
//    static final class TabInfo{
//        private final Class<?> clss;
//        private final Bundle args;
//
//        TabInfo(Class<?> _class, Bundle _args){
//            clss = _class;
//            args = _args;
//        }
//    }
//
//    public TabsAdapter (FragmentActivity activity, ViewPager pager) {
//        super(activity.getSupportFragmentManager());
//        mContext = activity;
//        mActionBar = activity.getActionBar();
//        mViewPager = pager;
//        mViewPager.setAdapter(this);
//        mViewPager.setOnPageChangeListener(this);
//    }
//
//    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args){
//        TabInfo info = new TabInfo(clss, args);
//        tab.setTag(info);
//        tab.setTabListener(this);
//        mTabs.add(info);
//        mActionBar.addTab(tab);
//        notifyDataSetChanged();
//    }
//}
