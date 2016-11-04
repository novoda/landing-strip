package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.fragment.DemoFragmentPagerAdapter;

public class MultipleListenersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Demo: " + getClass().getSimpleName());

        setContentView(R.layout.activity_multiple_listeners);

        LandingStrip landingStrip = (LandingStrip) findViewById(com.novoda.landing_strip.R.id.landing_strip);
        landingStrip.addOnPageChangeListener(firstOnPageChangedListener);
        landingStrip.addOnPageChangeListener(secondOnPageChangedListener);

        ViewPager viewPager = (ViewPager) findViewById(com.novoda.landing_strip.R.id.view_pager);
        viewPager.setAdapter(new DemoFragmentPagerAdapter(getSupportFragmentManager()));

        landingStrip.attach(viewPager, viewPager.getAdapter());
    }

    private final ViewPager.OnPageChangeListener firstOnPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            // no-op
        }

        @Override
        public void onPageSelected(int i) {
            Toast.makeText(MultipleListenersActivity.this, "First listener", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //no-op
        }
    };

    private final ViewPager.OnPageChangeListener secondOnPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            // no-op
        }

        @Override
        public void onPageSelected(int i) {
            Toast.makeText(MultipleListenersActivity.this, "Second listener", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //no-op
        }
    };
}
