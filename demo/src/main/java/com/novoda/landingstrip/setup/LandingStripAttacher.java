package com.novoda.landingstrip.setup;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.landing_strip.R;
import com.novoda.landingstrip.LandingStrip;

public class LandingStripAttacher {

    private final ViewPager viewPager;
    private final LandingStrip landingStrip;
    private DataSetObserver dataSetObserver;

    public LandingStripAttacher(ViewPager viewPager, LandingStrip landingStrip) {
        this.viewPager = viewPager;
        this.landingStrip = landingStrip;
    }

    public void attach() {
        LayoutInflater layoutInflater = LayoutInflater.from(viewPager.getContext());
        viewPager.addOnPageChangeListener(landingStrip);

        PagerAdapter pagerAdapter = viewPager.getAdapter();
        final SimpleTabAdapter tabAdapter = new SimpleTabAdapter(pagerAdapter, layoutInflater, viewPager);

        dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                tabAdapter.notifyDataSetChanged();
            }
        };
        pagerAdapter.registerDataSetObserver(dataSetObserver);
        landingStrip.setAdapter(tabAdapter);
    }

    public void detach() {
        viewPager.removeOnPageChangeListener(landingStrip);
        if (dataSetObserver == null) {
            return;
        }
        viewPager.getAdapter().unregisterDataSetObserver(dataSetObserver);
    }

    private static class SimpleTabAdapter extends LandingStrip.Adapter<TextView> {

        private final PagerAdapter pagerAdapter;
        private final LayoutInflater layoutInflater;
        private final ViewPager viewPager;

        SimpleTabAdapter(PagerAdapter pagerAdapter, LayoutInflater layoutInflater, ViewPager viewPager) {
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

}
