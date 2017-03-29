package com.novoda.landingstrip;

interface State {

    int position();

    float offset();

    int fastForwardPosition();

    boolean firstTimeAccessed();

    int selectedPosition();
}
