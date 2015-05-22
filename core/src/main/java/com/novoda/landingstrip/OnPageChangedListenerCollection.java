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
    public void onPageScrolled(int i, float v, int i1) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageScrolled(i, v, i1);
        }
    }

    @Override
    public void onPageSelected(int i) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageSelected(i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        for (ViewPager.OnPageChangeListener listener : listeners) {
            listener.onPageScrollStateChanged(i);
        }
    }

    public void add(ViewPager.OnPageChangeListener onPageChangeListener) {
        listeners.add(onPageChangeListener);
    }
}
