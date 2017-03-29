package com.novoda.landingstrip;

import android.support.v4.view.ViewPager;

class ScrollingPageChangeListener implements ViewPager.OnPageChangeListener {

    private final MutableState state;
    private final TabsContainerView tabsContainerView;
    private final ScrollOffsetCalculator scrollOffsetCalculator;
    private final Scrollable scrollable;
    private final FastForwarder fastForwarder;

    ScrollingPageChangeListener(MutableState state,
                                TabsContainerView tabsContainerView,
                                ScrollOffsetCalculator scrollOffsetCalculator,
                                Scrollable scrollable,
                                FastForwarder fastForwarder) {
        this.state = state;
        this.tabsContainerView = tabsContainerView;
        this.scrollOffsetCalculator = scrollOffsetCalculator;
        this.scrollable = scrollable;
        this.fastForwarder = fastForwarder;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (shouldHandleFastForward()) {
            handleFastForward(position, positionOffset);
        } else {
            scroll(position, positionOffset);
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
        state.updateSelectedPosition(position);
        tabsContainerView.setActivated(position);
    }

    @Override
    public void onPageScrollStateChanged(int changedState) {
        // no op
    }

}
