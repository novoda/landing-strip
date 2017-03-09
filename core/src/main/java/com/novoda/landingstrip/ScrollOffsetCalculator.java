package com.novoda.landingstrip;

import android.view.View;

class ScrollOffsetCalculator {

    private static final float HALF_MULTIPLIER = 0.5f;
    private static final float ROUNDING_OFFSET = 0.5f;

    private final TabsContainer tabsContainer;

    ScrollOffsetCalculator(TabsContainer tabsContainer) {
        this.tabsContainer = tabsContainer;
    }

    int calculateScrollOffset(int position, float pagerOffset) {
        if (!tabsContainer.hasTabAt(position)) {
            return 0;
        } else {
            View tabForPosition = tabsContainer.getTabAt(position);

            float tabStartX = tabForPosition.getLeft() + getHorizontalScrollOffset(tabForPosition, pagerOffset);
            float viewMiddleOffset = getTabParentWidth() * HALF_MULTIPLIER;
            float tabCenterOffset = (tabForPosition.getRight() - tabForPosition.getLeft()) * HALF_MULTIPLIER;
            float nextTabDelta = getNextTabDelta(position, pagerOffset, tabForPosition);

            return roundToInt(tabStartX - viewMiddleOffset + tabCenterOffset + nextTabDelta);
        }
    }

    private int roundToInt(float input) {
        return (int) (input + ROUNDING_OFFSET);
    }

    private int getTabParentWidth() {
        return tabsContainer.getParentWidth();
    }

    private int getHorizontalScrollOffset(View tab, float pagerOffset) {
        int tabWidth = tab.getWidth();
        return Math.round(pagerOffset * tabWidth);
    }

    private float getNextTabDelta(int position, float pagerOffset, View tabForPosition) {
        if (tabsContainer.hasTabAt(position + 1)) {
            return (((tabsContainer.getTabAt(position + 1).getWidth()) - tabForPosition.getWidth()) * pagerOffset) * HALF_MULTIPLIER;
        }
        return 0F;
    }

}
