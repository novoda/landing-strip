package com.novoda.landingstrip;

import android.view.View;

class Notifier implements LandingStripAdapter.Listener {

    private final TabsContainerView tabsContainerView;

    Notifier(TabsContainerView tabsContainerView) {
        this.tabsContainerView = tabsContainerView;
    }

    @Override
    public <T extends View> void onNotifyDataSetChanged(LandingStripAdapter<T> adapter) {
        recreateAndBindTabs(adapter);
    }

    private <T extends View> void recreateAndBindTabs(LandingStripAdapter<T> adapter) {
        tabsContainerView.removeAllViews();

        for (int position = 0; position < adapter.getCount(); position++) {
            T view = adapter.createView(tabsContainerView, position);
            adapter.bindView(view, position);
            tabsContainerView.addView(view);
        }
    }

}
