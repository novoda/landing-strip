package com.novoda.landingstrip;

import android.support.v4.view.ViewPager;

class ScrollingPageChangeListener implements ViewPager.OnPageChangeListener {

    private final State state;
    private final TabsContainer tabsContainer;
    private final ScrollOffsetCalculator scrollOffsetCalculator;
    private final Scrollable scrollable;

    private boolean firstTimeAccessed = true;

    ScrollingPageChangeListener(State state, TabsContainer tabsContainer, ScrollOffsetCalculator scrollOffsetCalculator, Scrollable scrollable) {
        this.state = state;
        this.tabsContainer = tabsContainer;
        this.scrollOffsetCalculator = scrollOffsetCalculator;
        this.scrollable = scrollable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        handleAdapterSetBecausePageSelectedIsNotCalled(position);

        if (state.fastForwardPositionIsValid()) {
            fastForward();
            if (fastForwardPositionReached(position, positionOffset)) {
                state.invalidateFastForwardPosition();
            }
        } else {
            scroll(position, positionOffset);
        }

        state.getDelegateOnPageListener().onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    private void handleAdapterSetBecausePageSelectedIsNotCalled(int position) {
        if (firstTimeAccessed) {
            tabsContainer.setSelected(position);
            firstTimeAccessed = false;
        }
    }

    private boolean fastForwardPositionReached(int position, float positionOffset) {
        return position == state.getFastForwardPosition() && positionOffset == 0F;
    }

    private void fastForward() {
        scroll(state.getFastForwardPosition(), 0);
    }

    private void scroll(int position, float positionOffset) {
        state.updatePosition(position);
        state.updatePositionOffset(positionOffset);

        scrollable.scrollTo(scrollOffsetCalculator.calculateScrollOffset(position, positionOffset));
    }

    @Override
    public void onPageSelected(int position) {
        tabsContainer.setSelected(position);
        state.getDelegateOnPageListener().onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int changedState) {
        state.getDelegateOnPageListener().onPageScrollStateChanged(changedState);
    }

}
