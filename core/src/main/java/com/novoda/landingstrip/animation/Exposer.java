package com.novoda.landingstrip.animation;

import android.animation.ValueAnimator;

public class Exposer {

    public static Animator animator() {
        return ViewUtilsAnimator.newInstance();
    }

    public interface Animator {
        void setDuration(int duration);

        void setUpdateListener(UpdateListener updateListener);

        int getAnimatedIntValue();

        void setIntValues(int from, int to);

        void start();

        interface UpdateListener {
            void onUpdate(Animator animator);
        }

    }

    private static class ViewUtilsAnimator implements Animator {

        private final ValueAnimator animator;

        static ViewUtilsAnimator newInstance() {
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
            return new ViewUtilsAnimator(valueAnimator);
        }

        public ViewUtilsAnimator(ValueAnimator animator) {
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
                    updateListener.onUpdate(new ViewUtilsAnimator(animation));
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
}
