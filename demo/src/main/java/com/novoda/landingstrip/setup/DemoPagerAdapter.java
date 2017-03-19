package com.novoda.landingstrip.setup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

public class DemoPagerAdapter extends FragmentPagerAdapter {

    private final Data[] data;

    public static DemoPagerAdapter newInstance(AppCompatActivity activity, Data[] values) {
        return new DemoPagerAdapter(activity.getSupportFragmentManager(), values);
    }

    public DemoPagerAdapter(FragmentManager fm, Data[] data) {
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
