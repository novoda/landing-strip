package com.novoda.landingstrip;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.landing_strip.R;

public class TabAdapter extends LandingStrip.Adapter<TextView> {

    private final PagerAdapter pagerAdapter;
    private final LayoutInflater layoutInflater;
    private final ViewPager viewPager;

    public TabAdapter(PagerAdapter pagerAdapter, LayoutInflater layoutInflater, ViewPager viewPager) {
        this.pagerAdapter = pagerAdapter;
        this.layoutInflater = layoutInflater;
        this.viewPager = viewPager;
    }

    @Override
    protected TextView createView(ViewGroup parent, int position) {
        return (TextView) layoutInflater.inflate(R.layout.tab_simple_text, parent, false);
    }

    @Override
    protected void bindView(final TextView view, final int position, final LandingStrip landingStrip) {
        CharSequence pageTitle = pagerAdapter.getPageTitle(position);
        view.setText(pageTitle);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingStrip.moveToPosition(position);
                viewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    protected int getCount() {
        return pagerAdapter.getCount();
    }
}
