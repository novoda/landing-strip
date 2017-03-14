package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.Data;
import com.novoda.landingstrip.setup.fragment.DemoFragmentPagerAdapter;

public class CustomLandingStrip extends AppCompatActivity {

    private LandingStrip landingStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_landing_strip);
        setTitle("Demo: " + getClass().getSimpleName());

        landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new DemoFragmentPagerAdapter(getSupportFragmentManager(), Data.values()));
    }

    @Override
    protected void onDestroy() {
        landingStrip.detach();
        super.onDestroy();
    }
}
