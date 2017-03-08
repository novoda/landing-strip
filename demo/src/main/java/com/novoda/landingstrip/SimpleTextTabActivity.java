package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
        viewPager.setAdapter(new DemoFragmentPagerAdapter(getSupportFragmentManager()));

        landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
        landingStrip.attach(viewPager);
    }

    @Override
    protected void onDestroy() {
        landingStrip.detach();
        super.onDestroy();
    }
}
