package com.novoda.landingstrip;

import android.view.View;

class Notifier<T extends View> implements BaseAdapter.Listener<T> {

    private final TabsContainerView tabsContainerView;
    private final MutableState state;

    Notifier(TabsContainerView tabsContainerView, MutableState state) {
        this.tabsContainerView = tabsContainerView;
        this.state = state;
    }

    @Override
    public void onNotifyDataSetChanged(BaseAdapter<T> adapter) {
        recreateAndBindTabs(adapter);
        initializeSelectedPosition();
    }

    private void recreateAndBindTabs(BaseAdapter<T> adapter) {
        tabsContainerView.removeAllViews();

        for (int position = 0; position < adapter.getCount(); position++) {
            T view = adapter.createView(tabsContainerView, position);
            adapter.bindView(view, position);
            tabsContainerView.addView(view);
        }
    }

    private void initializeSelectedPosition() {
        tabsContainerView.setActivated(state.selectedPosition());
        state.updateFirstTimeAccessed(false);
    }

    @Override
    public void onNotifyItemChanged(BaseAdapter<T> adapter, int position) {
        View tabView = tabsContainerView.getChildAt(position);
        adapter.bindView((T) tabView, position);
    }

}
