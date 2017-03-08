package com.novoda.landingstrip;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class TabsContainerView extends LinearLayout {

    public TabsContainerView(Context context) {
        super(context);
        super.setOrientation(HORIZONTAL);
    }

    public void setActivated(int position) {
        for (int index = 0; index < getChildCount(); index++) {
            View tab = getChildAt(index);
            tab.setActivated(index == position);
        }
    }
}
