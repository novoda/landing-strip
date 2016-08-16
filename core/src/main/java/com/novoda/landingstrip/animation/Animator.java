package com.novoda.landingstrip.animation;

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
