package com.novoda.landingstrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class LandingStrip extends HorizontalScrollView implements Scrollable, ViewPager.OnPageChangeListener {

    private final Attributes attributes;
    private final Paint indicatorPaint;
    private final State state;
    private final IndicatorCoordinatesCalculator indicatorCoordinatesCalculator;

    private TabsContainerView tabsContainerView;
    private ScrollingPageChangeListener scrollingPageChangeListener;

    public LandingStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFillViewport(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);

        this.attributes = Attributes.readAttributes(context, attrs);
        this.indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(getResources().getColor(attributes.getIndicatorColor()));

        this.state = State.newInstance();
        this.indicatorCoordinatesCalculator = IndicatorCoordinatesCalculator.newInstance();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.tabsContainerView = new TabsContainerView(getContext());
        tabsContainerView.setPadding(attributes.getTabsPaddingLeft(), 0, attributes.getTabsPaddingRight(), 0);
        addView(tabsContainerView);

        ScrollOffsetCalculator scrollOffsetCalculator = new ScrollOffsetCalculator(this, tabsContainerView);
        FastForwarder fastForwarder = new FastForwarder(state, this, scrollOffsetCalculator);

        this.scrollingPageChangeListener = new ScrollingPageChangeListener(
                state,
                tabsContainerView,
                scrollOffsetCalculator,
                this,
                fastForwarder
        );
    }

    public <T extends View> void setAdapter(Adapter<T> adapter) {
        adapter.setListener(new Adapter.Listener<T>() {
            @Override
            public void onNotifyDataSetChanged(Adapter<T> adapter) {
                createAndBindTabs(adapter);
            }
        });

        createAndBindTabs(adapter);
    }

    private <T extends View> void createAndBindTabs(Adapter<T> adapter) {
        tabsContainerView.removeAllViews();

        for (int position = 0; position < adapter.getCount(); position++) {
            T view = adapter.createView(tabsContainerView, position);
            adapter.bindView(view, position, this);
            tabsContainerView.addView(view);
        }
    }

    public void moveToPosition(int position) {
        if (alreadyAt(position)) {
            return;
        }
        state.updateFastForwardPosition(position);
        forceDrawIndicatorAtPosition(position);
    }

    private boolean alreadyAt(int position) {
        return position != state.getPosition();
    }

    private void forceDrawIndicatorAtPosition(int position) {
        state.updatePosition(position);
        state.updatePositionOffset(0);
        invalidate();
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (tabsContainerView.getChildCount() == 0) {
            return;
        }

        Coordinates indicatorCoordinates = indicatorCoordinatesCalculator.calculate(
                state.getPosition(),
                state.getPagePositionOffset(),
                tabsContainerView
        );
        drawIndicator(canvas, indicatorCoordinates);
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

    public void detach() {
        // no op
    }

    public abstract static class Adapter<T extends View> {

        private Listener<T> listener;

        protected abstract T createView(ViewGroup parent, int position);

        protected abstract void bindView(T view, int position, LandingStrip landingStrip);

        void setListener(Listener<T> listener) {
            this.listener = listener;
        }

        public void notifyDataSetChanged() {
            if (listener != null) {
                listener.onNotifyDataSetChanged(this);
            }
        }

        protected abstract int getCount();

        interface Listener<T extends View> {

            void onNotifyDataSetChanged(Adapter<T> adapter);

        }
    }
}
