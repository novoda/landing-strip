package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.fragment.DemoFragmentPagerAdapter;

public class SimpleTextTabActivity extends AppCompatActivity {

    private LandingStrip landingStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_usage);
        setTitle("Demo: " + getClass().getSimpleName());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        DemoFragmentPagerAdapter pagerAdapter = new DemoFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
        landingStrip.setAdapter(new TabAdapter(pagerAdapter, LayoutInflater.from(this)));

        viewPager.addOnPageChangeListener(landingStrip);
    }

    @Override
    protected void onDestroy() {
        landingStrip.detach();
        super.onDestroy();
    }
}
