package com.novoda.landingstrip;

import android.view.View;
import android.view.ViewGroup;

class ScrollOffsetCalculator {

    private static final float HALF_MULTIPLIER = 0.5f;
    private static final float ROUNDING_OFFSET = 0.5f;

    private final View rootView;
    private final ViewGroup tabsContainer;

    ScrollOffsetCalculator(View rootView, TabsContainerView tabsContainer) {
        this.rootView = rootView;
        this.tabsContainer = tabsContainer;
    }

    int calculateScrollOffset(int position, float pagerOffset) {
        View tabForPosition = tabsContainer.getChildAt(position);

        float tabStartX = tabForPosition.getLeft() + getHorizontalScrollOffset(position, pagerOffset);
        float viewMiddleOffset = rootView.getWidth() * HALF_MULTIPLIER;
        float tabCenterOffset = (tabForPosition.getRight() - tabForPosition.getLeft()) * HALF_MULTIPLIER;
        float nextTabDelta = getNextTabDelta(position, pagerOffset, tabForPosition);

        return roundToInt(tabStartX - viewMiddleOffset + tabCenterOffset + nextTabDelta);
    }

    private int roundToInt(float input) {
        return (int) (input + ROUNDING_OFFSET);
    }

    private int getHorizontalScrollOffset(int position, float pagerOffset) {
        int tabWidth = tabsContainer.getChildAt(position).getWidth();
        return Math.round(pagerOffset * tabWidth);
    }

    private float getNextTabDelta(int position, float pagerOffset, View tabForPosition) {
        if (hasTabAt(position + 1)) {
            return (((tabsContainer.getChildAt(position + 1).getWidth()) - tabForPosition.getWidth()) * pagerOffset) * HALF_MULTIPLIER;
        }
        return 0F;
    }

    private boolean hasTabAt(int position) {
        return tabsContainer.getChildCount() - 1 >= position + 1;
    }

}
