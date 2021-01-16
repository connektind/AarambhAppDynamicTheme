package com.example.aarambhappdynamictheme.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.aarambhappdynamictheme.fragment.DynamicBookMarkFragment;


public class BookMarkPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public BookMarkPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return DynamicBookMarkFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

