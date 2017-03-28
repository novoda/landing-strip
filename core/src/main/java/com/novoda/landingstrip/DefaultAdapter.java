package com.novoda.landingstrip;

import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DefaultAdapter<T extends View> extends LandingStripAdapter<T> {

    private final LayoutInflater layoutInflater;
    @LayoutRes
    private final int itemLayoutId;
    private final LandingStrip landingStrip;
    private final ViewPager viewPager;

    public DefaultAdapter(LayoutInflater layoutInflater, int itemLayoutId, LandingStrip landingStrip, ViewPager viewPager) {
        this.layoutInflater = layoutInflater;
        this.itemLayoutId = itemLayoutId;
        this.landingStrip = landingStrip;
        this.viewPager = viewPager;
    }

    @Override
    protected T createView(ViewGroup parent, int position) {
        return (T) layoutInflater.inflate(itemLayoutId, parent, false);
    }

    @Override
    protected void bindView(T view, final int position) {
        bindTabClickListener(view, position);
    }

    protected void bindTabClickListener(T view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landingStrip.setCurrentItem(position);
                viewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    protected int getCount() {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            return 0;
        } else {
            return adapter.getCount();
        }
    }

}
