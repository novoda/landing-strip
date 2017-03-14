package com.novoda.landingstrip.setup.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.novoda.landingstrip.setup.Data;

public class DemoFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Data[] data;

    public DemoFragmentPagerAdapter(FragmentManager fm, Data[] data) {
        super(fm);
        this.data = data;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data[position].getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerFragment.newInstance(data[position]);
    }

    @Override
    public int getCount() {
        return data.length;
    }
}
