package com.novoda.landingstrip;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

class TabsContainer {

    private final LinearLayout tabsContainerView;

    TabsContainer(LinearLayout tabsContainerView) {
        this.tabsContainerView = tabsContainerView;
    }

    void clearTabs() {
        tabsContainerView.removeAllViews();
    }

    View inflateTab(LayoutInflater layoutInflater, int layoutId) {
        return layoutInflater.inflate(layoutId, tabsContainerView, false);
    }

    void addTab(View tabView, int position) {
        tabsContainerView.addView(tabView, position, tabView.getLayoutParams());
    }

    boolean hasTabs() {
        return tabsContainerView.getChildCount() > 0;
    }

    View getTabAt(int position) {
        return tabsContainerView.getChildAt(position);
    }

    boolean isEmpty() {
        return !hasTabs();
    }

    void startWatching(final ViewPager viewPager, final ViewPager.OnPageChangeListener onPageChangeListener) {
        if (hasTabs()) {
            getTabAt(0).getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            ViewTreeObserver observer = getTabAt(0).getViewTreeObserver();
                            removeOnGlobalLayoutListener(observer, this);
                            viewPager.addOnPageChangeListener(onPageChangeListener);

                            onPageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
                        }
                    }
            );
        }
    }

    int getTabCount() {
        return tabsContainerView.getChildCount();
    }

    void setActivated(int position) {
        for (int index = 0; index < getTabCount(); index++) {
            View tab = getTabAt(index);
            tab.setActivated(index == position);
        }
    }

    int getParentWidth() {
        return ((View) tabsContainerView.getParent()).getWidth();
    }

    boolean hasTabAt(int position) {
        return getTabCount() - 1 >= position + 1;
    }

    private void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            removeOnGlobalLayoutListenerJellyBean(observer, victim);
        } else {
            removeOnGlobalLayoutListenerLegacy(observer, victim);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListenerJellyBean(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener victim) {
        observer.removeOnGlobalLayoutListener(victim);
    }

    @SuppressWarnings("deprecation")
    private void removeOnGlobalLayoutListenerLegacy(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener victim) {
        observer.removeGlobalOnLayoutListener(victim);
    }

    ViewGroup getContainer() {
        return tabsContainerView;
    }
}
