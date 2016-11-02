package com.novoda.landingstrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class LandingStrip extends HorizontalScrollView implements Scrollable, ViewPager.OnPageChangeListener {

    private final Attributes attributes;
    private final LayoutInflater layoutInflater;
    private final Paint indicatorPaint;
    private final State state;
    private final IndicatorCoordinatesCalculator indicatorCoordinatesCalculator;
    private final PagerAdapterObserver pagerAdapterObserver;
    private final ScrollOffsetCalculator scrollOffsetCalculator;
    private final FastForwarder fastForwarder;

    private TabsContainer tabsContainer;
    private OnTabClickListener onTabClickListener;
    private ScrollingPageChangeListener scrollingPageChangeListener;
    private TabAdapter tabAdapter;
    private OnPageChangedListenerCollection onPageChangeListenerCollection;

    public LandingStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFillViewport(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);

        this.attributes = Attributes.readAttributes(context, attrs);
        this.state = State.newInstance();
        this.layoutInflater = LayoutInflater.from(context);
        this.indicatorPaint = new Paint();
        this.indicatorCoordinatesCalculator = IndicatorCoordinatesCalculator.newInstance();
        this.pagerAdapterObserver = new PagerAdapterObserver(onPagerAdapterChangedListener);
        this.scrollOffsetCalculator = new ScrollOffsetCalculator(tabsContainer);
        this.fastForwarder = new FastForwarder(state, this, scrollOffsetCalculator);

        this.onPageChangeListenerCollection = OnPageChangedListenerCollection.newInstance();

        state.updatePosition(0);
        state.updatePositionOffset(0);

        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(getResources().getColor(attributes.getIndicatorColor()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_landing_strip, this);
        LinearLayout tabsContainerView = (LinearLayout) findViewById(R.id.landing_strip__container);
        tabsContainerView.setPadding(attributes.getTabsPaddingLeft(), 0, attributes.getTabsPaddingRight(), 0);
        tabsContainer = new TabsContainer(tabsContainerView);
        scrollingPageChangeListener = new ScrollingPageChangeListener(
                state,
                tabsContainer,
                scrollOffsetCalculator,
                this,
                fastForwarder
        );
    }

    private final OnPagerAdapterChangedListener onPagerAdapterChangedListener = new OnPagerAdapterChangedListener() {
        @Override
        public void onPagerAdapterChanged(PagerAdapter pagerAdapter) {
            rebindAllTabs();
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scrollingPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        scrollingPageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        scrollingPageChangeListener.onPageScrollStateChanged(state);
    }

    public interface TabAdapter {

        View getView(int position, ViewGroup parent);

        int getCount();

    }

    public void setAdapter(TabAdapter tabAdapter, OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        this.tabAdapter = tabAdapter;
        rebindAllTabs();
        // TODO: bind dataset observer
    }

    void rebindAllTabs() {
        tabsContainer.clearTabs();
        if (tabAdapter == null) {
            return;
        }

        for (int i = 0; i < tabAdapter.getCount(); i++) {
            View tab = tabAdapter.getView(i, tabsContainer.getContainer());
            final int position = i;
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notAlreadyAt(position)) {
                        state.updateFastForwardPosition(position);
                        forceDrawIndicatorAtPosition(position);
                    }
                    onTabClickListener.onTabClicked(position);
                }
            });
            tabsContainer.addTab(tab, position);
        }
    }

    // pagerAdapter.register(foo);

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        onPageChangeListenerCollection.add(onPageChangeListener);
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (tabsContainer.isEmpty()) {
            return;
        }

        drawIndicator(canvas, indicatorCoordinatesCalculator.calculate(state.getPosition(), state.getPagePositionOffset(), tabsContainer));
    }

    protected void drawIndicator(Canvas canvas, Coordinates indicatorCoordinates) {
        int height = getHeight();
        canvas.drawRect(
                indicatorCoordinates.getStart(),
                height - attributes.getIndicatorHeight(),
                indicatorCoordinates.getEnd(),
                height,
                indicatorPaint
        );
    }

    @Override
    public void scrollTo(int x) {
        scrollTo(x, getBottom());
    }

    @Override
    public int getCurrentScrollX() {
        return getScrollX();
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pagerAdapterObserver.reregister();
    }

    @Override
    protected void onDetachedFromWindow() {
        pagerAdapterObserver.unregister();
        super.onDetachedFromWindow();
    }

    private boolean notAlreadyAt(int position) {
        return position != state.getPosition();
    }

    private void forceDrawIndicatorAtPosition(int position) {
        state.updatePosition(position);
        state.updatePositionOffset(0);
        invalidate();
    }

    public interface OnTabClickListener {
        void onTabClicked(int position);
    }

}
