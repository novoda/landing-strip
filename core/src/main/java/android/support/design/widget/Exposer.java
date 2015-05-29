package android.support.design.widget;

public class Exposer {


    public static Animator animator() {
        return new ViewUtilsAnimator(ViewUtils.createAnimator());
    }

    public interface Animator {
        void setDuration(int duration);
        void setUpdateListener(UpdateListener updateListener);
        int getAnimatedIntValue();
        void setIntValues(int from, int to);
        void start();
    }

    public interface UpdateListener {
        void onUpdate(Animator animator);
    }

    private static class ViewUtilsAnimator implements Animator {

        private final ValueAnimatorCompat animator;

        static ViewUtilsAnimator newInstance() {
            ValueAnimatorCompat animator = ViewUtils.createAnimator();
            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            return new ViewUtilsAnimator(animator);
        }

        public ViewUtilsAnimator(ValueAnimatorCompat animator) {
            this.animator = animator;
        }

        @Override
        public void setDuration(int duration) {
            animator.setDuration(duration);
        }

        @Override
        public void setUpdateListener(final UpdateListener updateListener) {
            animator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                  updateListener.onUpdate(new ViewUtilsAnimator(valueAnimatorCompat));
                }
            });
        }

        @Override
        public int getAnimatedIntValue() {
            return animator.getAnimatedIntValue();
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
