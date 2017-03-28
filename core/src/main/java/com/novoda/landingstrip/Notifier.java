package com.novoda.landingstrip;

import android.view.View;

class Notifier<T extends View> implements BaseAdapter.Listener<T> {

    private final TabsContainerView tabsContainerView;

    Notifier(TabsContainerView tabsContainerView) {
        this.tabsContainerView = tabsContainerView;
    }

    @Override
    public void onNotifyDataSetChanged(BaseAdapter<T> adapter) {
        recreateAndBindTabs(adapter);
    }

    private void recreateAndBindTabs(BaseAdapter<T> adapter) {
        tabsContainerView.removeAllViews();

        for (int position = 0; position < adapter.getCount(); position++) {
            T view = adapter.createView(tabsContainerView, position);
            adapter.bindView(view, position);
            tabsContainerView.addView(view);
        }
    }

    @Override
    public void onNotifyItemChanged(BaseAdapter<T> adapter, int position) {
        View tabView = tabsContainerView.getChildAt(position);
        adapter.bindView((T) tabView, position);
    }

}
