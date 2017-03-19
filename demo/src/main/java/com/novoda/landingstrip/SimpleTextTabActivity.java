package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
        populateTabs(viewPager, EXAMPLE_DATA);
    }

    private void populateViewPager(ViewPager viewPager, Data[] data) {
        DemoPagerAdapter pagerAdapter = DemoPagerAdapter.newInstance(this, data);
        viewPager.setAdapter(pagerAdapter);
    }

    private void populateTabs(final ViewPager viewPager, Data[] data) {
        final LandingStrip landingStrip = (LandingStrip) findViewById(R.id.landing_strip);

        ExampleTabAdapter.OnTabClickListener setCurrentItemOnClick = new ExampleTabAdapter.OnTabClickListener() {
            @Override
            public void onTabClicked(int position, Data data) {
                landingStrip.setCurrentItem(position);
                viewPager.setCurrentItem(position);
            }
        };
        ExampleTabAdapter tabAdapter = new ExampleTabAdapter(getLayoutInflater(), setCurrentItemOnClick, data);

        viewPager.addOnPageChangeListener(landingStrip);
        landingStrip.setAdapter(tabAdapter);
    }

}
