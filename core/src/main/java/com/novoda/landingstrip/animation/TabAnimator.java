package com.novoda.landingstrip.animation;

import android.animation.ValueAnimator;

public class TabAnimator implements Animator {

    private final ValueAnimator animator;

    public static TabAnimator newInstance() {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new FastOutSlowInInterpolator());
        return new TabAnimator(animator);
    }

    public TabAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    @Override
    public void setDuration(int duration) {
        animator.setDuration(duration);
    }

    @Override
    public void setUpdateListener(final UpdateListener updateListener) {
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateListener.onUpdate(new TabAnimator(animation));

            }
        });
    }

    @Override
    public int getAnimatedIntValue() {
        return (int) animator.getAnimatedValue();
    }

    @Override
    public void setIntValues(int from, int to) {
        animator.setIntValues(from, to);
    }

    @Override
    public void start() {
        animator.start();
    }
}
