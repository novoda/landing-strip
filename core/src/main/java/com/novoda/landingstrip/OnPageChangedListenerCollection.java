package com.novoda.landingstrip;

import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

class OnPageChangedListenerCollection implements ViewPager.OnPageChangeListener {

    private final List<ViewPager.OnPageChangeListener> listeners;

    static OnPageChangedListenerCollection newInstance() {
        return new OnPageChangedListenerCollection(new ArrayList<ViewPager.OnPageChangeListener>());
    }

    OnPageChangedListenerCollection(List<ViewPager.OnPageChangeListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageScrollStateChanged(state);
        }
    }

    public void add(ViewPager.OnPageChangeListener onPageChangeListener) {
        listeners.add(onPageChangeListener);
    }
}
