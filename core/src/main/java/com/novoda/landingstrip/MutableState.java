package com.novoda.landingstrip;

class MutableState implements State {

    private float pagePositionOffset;
    private int position;
    private int fastForwardPosition;
    private int selectedPosition;

    public static MutableState newInstance() {
        MutableState state = new MutableState();

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

    public void updateSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public float offset() {
        return pagePositionOffset;
    }

    @Override
    public int position() {
        return position;
    }

    @Override
    public int fastForwardPosition() {
        return fastForwardPosition;
    }

    @Override
    public int selectedPosition() {
        return selectedPosition;
    }
}
