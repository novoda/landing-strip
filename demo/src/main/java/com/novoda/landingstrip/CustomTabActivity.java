package com.novoda.landingstrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.Data;
import com.novoda.landingstrip.setup.fragment.DemoFragmentPagerAdapter;

public class CustomTabActivity extends AppCompatActivity {

    private static final int TAB_TITLE_RESOURCE = R.id.tab_2_title;
    private static final int TAB_CONTENT_RESOURCE = R.id.tab_2_content;
    private static final String TAG = CustomTabActivity.class.getSimpleName();

    private LandingStrip landingStrip;
    private DemoFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab);
        setTitle("Demo: " + getClass().getSimpleName());

        landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new DemoFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        landingStrip.attach(viewPager, viewPager.getAdapter(), customTabs);
    }

    @Override
    protected void onDestroy() {
        landingStrip.detach();
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            TextView text = (TextView) landingStrip.getTabAt(i).findViewById(TAB_TITLE_RESOURCE);
            Log.v(TAG, "Title for tab number " + i + " is: " + text.getText());
        }
    }

    private final LandingStrip.TabSetterUpper customTabs = new LandingStrip.TabSetterUpper() {
        @Override
        public View setUp(int position, CharSequence title, View inflatedTab) {
            ((TextView) inflatedTab.findViewById(TAB_TITLE_RESOURCE)).setText("" + position);
            ((ImageView) inflatedTab.findViewById(TAB_CONTENT_RESOURCE)).setImageResource(Data.values()[position].getResId());

            return inflatedTab;
        }
    };

}
