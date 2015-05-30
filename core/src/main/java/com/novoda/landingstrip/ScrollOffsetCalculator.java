package com.novoda.landingstrip;

import android.view.View;

class ScrollOffsetCalculator {

    private final TabsContainer tabsContainer;

    ScrollOffsetCalculator(TabsContainer tabsContainer) {
        this.tabsContainer = tabsContainer;
    }

    int calculateScrollOffset(int position, float pagerOffset) {
        View tabForPosition = tabsContainer.getTabAt(position);

        float tabStartX = tabForPosition.getLeft() + getHorizontalScrollOffset(position, pagerOffset);

        int viewMiddleOffset = getTabParentWidth() / 2;
        float tabCenterOffset = (tabForPosition.getRight() - tabForPosition.getLeft()) * 0.5F;

        float nextTabDelta = getNextTabDelta(position, pagerOffset, tabForPosition);

        return (int) (tabStartX - viewMiddleOffset + tabCenterOffset + nextTabDelta);
    }

    private int getTabParentWidth() {
        return tabsContainer.getParentWidth();
    }

    private int getHorizontalScrollOffset(int position, float pagerOffset) {
        int tabWidth = tabsContainer.getTabAt(position).getWidth();
        return Math.round(pagerOffset * tabWidth);
    }

    private float getNextTabDelta(int position, float pagerOffset, View tabForPosition) {
        if (tabsContainer.getTabCount() - 1 >= position + 1) {
            return (((tabsContainer.getTabAt(position + 1).getWidth()) - tabForPosition.getWidth()) * pagerOffset) * 0.5F;
        }
        return 0F;
    }

}
