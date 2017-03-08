package com.novoda.landingstrip;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.landing_strip.R;

public class TabAdapter extends LandingStrip.Adapter<TextView> {

    private final PagerAdapter pagerAdapter;
    private final LayoutInflater layoutInflater;

    public TabAdapter(PagerAdapter pagerAdapter, LayoutInflater layoutInflater) {
        this.pagerAdapter = pagerAdapter;
        this.layoutInflater = layoutInflater;
    }

    @Override
    protected TextView createView(ViewGroup parent, int position) {
        return (TextView) layoutInflater.inflate(R.layout.tab_simple_text, parent, false);
    }

    @Override
    protected void bindView(TextView view, int position) {
        CharSequence pageTitle = pagerAdapter.getPageTitle(position);
        view.setText(pageTitle);
    }

    @Override
    protected int getCount() {
        return pagerAdapter.getCount();
    }
}
