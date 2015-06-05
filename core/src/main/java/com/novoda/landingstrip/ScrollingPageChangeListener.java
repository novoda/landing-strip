package com.novoda.landingstrip;

import android.support.v4.view.ViewPager;

class ScrollingPageChangeListener implements ViewPager.OnPageChangeListener {

    private final State state;
    private final TabsContainer tabsContainer;
    private final ScrollOffsetCalculator scrollOffsetCalculator;
    private final Scrollable scrollable;
    private final FastForwarder fastForwarder;
    private final OnPageChangedListenerCollection onPageChangedListenerCollection;

    private boolean firstTimeAccessed = true;

    ScrollingPageChangeListener(State state, TabsContainer tabsContainer, ScrollOffsetCalculator scrollOffsetCalculator,
                                Scrollable scrollable, FastForwarder fastForwarder, OnPageChangedListenerCollection onPageChangedListenerCollection) {
        this.state = state;
        this.tabsContainer = tabsContainer;
        this.scrollOffsetCalculator = scrollOffsetCalculator;
        this.scrollable = scrollable;
        this.fastForwarder = fastForwarder;
        this.onPageChangedListenerCollection = onPageChangedListenerCollection;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        handleAdapterSetBecausePageSelectedIsNotCalled(position);

        if (shouldHandleFastForward()) {
            handleFastForward(position, positionOffset);
        } else {
            scroll(position, positionOffset);
        }

        onPageChangedListenerCollection.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    private void handleAdapterSetBecausePageSelectedIsNotCalled(int position) {
        if (firstTimeAccessed) {
            tabsContainer.setActivated(position);
            firstTimeAccessed = false;
        }
    }

    private boolean shouldHandleFastForward() {
        return fastForwarder.shouldHandleFastForward();
    }

    private void handleFastForward(int position, float positionOffset) {
        if (fastForwarder.isIdle()) {
            fastForwarder.fastForward();
        }
        if (fastForwarder.isFinished(position, positionOffset)) {
            fastForwarder.reset();
        }
    }

    private void scroll(int position, float positionOffset) {
        state.updatePosition(position);
        state.updatePositionOffset(positionOffset);

        scrollable.scrollTo(scrollOffsetCalculator.calculateScrollOffset(position, positionOffset));
    }

    @Override
    public void onPageSelected(int position) {
        tabsContainer.setActivated(position);
        onPageChangedListenerCollection.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int changedState) {
        onPageChangedListenerCollection.onPageScrollStateChanged(changedState);
    }

}
