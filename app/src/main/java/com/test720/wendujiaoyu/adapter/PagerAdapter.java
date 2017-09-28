package com.test720.wendujiaoyu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by LuoPan on 2017/5/16 9:30.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    List<String> title;
    private FragmentManager fm;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        this.fm=fm;
        this.fragments = fragments;
        this.title=title;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE; //这个是必须的
    }


    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return    title.get(position);
    }

}
