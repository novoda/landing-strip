package com.novoda.landingstrip;

import android.support.v4.view.ViewPager;

public class LandingStripPageChangeListener {

    public static ViewPager.OnPageChangeListener create(LandingStrip landingStrip) {
        TabsContainerView tabsContainer = landingStrip.getTabsContainer();
        ScrollOffsetCalculator scrollOffsetCalculator = new ScrollOffsetCalculator(landingStrip, tabsContainer);
        MutableState mutableState = landingStrip.getMutableState();
        FastForwarder fastForwarder = new FastForwarder(mutableState, landingStrip, scrollOffsetCalculator);
        return new ScrollingPageChangeListener(
                mutableState,
                tabsContainer,
                scrollOffsetCalculator,
                landingStrip,
                fastForwarder
        );
    }

}
