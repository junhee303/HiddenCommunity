//package com.example.junhe.hiddencommunity;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//public class CustomViewPagerAdapter extends PagerAdapter {
//
//    private Context mContext;
//    private Vector<View> pages;
//
//    private final List<String> pageTitles = new ArrayList<>();
//
//    public CustomViewPagerAdapter(Context context, Vector<View> pages) {
//        this.mContext=context;
//        this.pages=pages;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View page = pages.get(position);
//        container.addView(page);
//        return page;
//    }
//
//    @Override
//    public int getCount() {
//        return pages.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view.equals(object);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
////    @Override
////    public CharSequence getPageTitle (int position) {
////        return "탭" + position; // 페이지(position)에 따른 tab의 타이틀 지정 ==> Major 값 받아와야함
////   }
//}