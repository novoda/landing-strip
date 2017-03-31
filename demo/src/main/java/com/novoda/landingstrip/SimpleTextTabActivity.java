package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.Data;
import com.novoda.landingstrip.setup.DemoPagerAdapter;

public class SimpleTextTabActivity extends AppCompatActivity {

    private static final Data[] EXAMPLE_DATA = Data.values();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_usage);
        setTitle("Demo: " + getClass().getSimpleName());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        populateViewPager(viewPager, EXAMPLE_DATA);
        populateTabs(viewPager);
    }

    private void populateViewPager(ViewPager viewPager, Data[] data) {
        DemoPagerAdapter pagerAdapter = DemoPagerAdapter.newInstance(this, data);
        viewPager.setAdapter(pagerAdapter);
    }

    private void populateTabs(final ViewPager viewPager) {
        LandingStrip landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
        TabAdapter tabAdapter = new TitledAdapter(
                getLayoutInflater(),
                R.layout.tab_simple_text,
                landingStrip,
                viewPager
        );
        landingStrip.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(landingStrip);
    }

    static class TitledAdapter extends TabAdapter<TextView> {

        private final ViewPager viewPager;

        public TitledAdapter(LayoutInflater layoutInflater, int itemLayoutId, LandingStrip landingStrip, ViewPager viewPager) {
            super(layoutInflater, itemLayoutId, landingStrip, viewPager);
            this.viewPager = viewPager;
        }

        @Override
        protected void bindView(TextView view, int position) {
            super.bindView(view, position);
            bindPageTitleToView(view, position);
        }

        private void bindPageTitleToView(TextView itemView, int position) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                return;
            }
            itemView.setText(adapter.getPageTitle(position));
        }

    }

}
