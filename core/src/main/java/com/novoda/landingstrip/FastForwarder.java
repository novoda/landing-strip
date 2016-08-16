package com.novoda.landingstrip;

import com.novoda.landingstrip.animation.Animator;
import com.novoda.landingstrip.animation.TabAnimator;

class FastForwarder {

    static final int BYPASS_FAST_FORWARD = -1;

    private final State state;
    private final Scrollable scrollable;
    private final ScrollOffsetCalculator scrollOffsetCalculator;

    private boolean fastForwarding = false;

    FastForwarder(State state, Scrollable scrollable, ScrollOffsetCalculator scrollOffsetCalculator) {
        this.state = state;
        this.scrollable = scrollable;
        this.scrollOffsetCalculator = scrollOffsetCalculator;
    }

    boolean shouldHandleFastForward() {
        return state.getFastForwardPosition() != BYPASS_FAST_FORWARD;
    }

    boolean isIdle() {
        return !fastForwarding;
    }

    void fastForward() {
        fastForwarding = true;

        animateToTab(state.getFastForwardPosition());
    }

    private void animateToTab(int newPosition) {
        int startScrollX = scrollable.getCurrentScrollX();
        int targetScrollX = scrollOffsetCalculator.calculateScrollOffset(newPosition, 0);
        Animator animator = TabAnimator.newInstance();
        animator.setDuration(150);
        animator.setUpdateListener(updateListener);
        animator.setIntValues(startScrollX, targetScrollX);
        animator.start();
    }

    private final Animator.UpdateListener updateListener = new Animator.UpdateListener() {
        @Override
        public void onUpdate(Animator animator) {
            scrollable.scrollTo(animator.getAnimatedIntValue());
        }
    };

    boolean isFinished(int position, float positionOffset) {
        return position == state.getFastForwardPosition() && positionOffset == 0F;
    }

    void reset() {
        fastForwarding = false;
        state.updateFastForwardPosition(BYPASS_FAST_FORWARD);
    }

}
