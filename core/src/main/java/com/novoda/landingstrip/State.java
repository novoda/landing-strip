package com.novoda.landingstrip;

class State {

    private float pagePositionOffset;
    private int position;
    private int fastForwardPosition;

    public static State newInstance() {
        State state = new State();

        state.updateFastForwardPosition(FastForwarder.BYPASS_FAST_FORWARD);
        state.updatePosition(0);
        state.updatePositionOffset(0f);

        return state;
    }

    public void updatePositionOffset(float positionOffset) {
        this.pagePositionOffset = positionOffset;
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    public void updateFastForwardPosition(int fastForwardPosition) {
        this.fastForwardPosition = fastForwardPosition;
    }

    public float getPagePositionOffset() {
        return pagePositionOffset;
    }

    public int getPosition() {
        return position;
    }

    public int getFastForwardPosition() {
        return fastForwardPosition;
    }

}
