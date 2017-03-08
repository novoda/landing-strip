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

    private static final int TAG_KEY_POSITION = R.id.ls__tag_key_position;

    private final Attributes attributes;
    private final Paint indicatorPaint;
    private final State state;
    private final IndicatorCoordinatesCalculator indicatorCoordinatesCalculator;

    private ViewPager viewPager;
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

        scrollingPageChangeListener = new ScrollingPageChangeListener(
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
            adapter.bindView(view, position);

            view.setOnClickListener(onTabClick);
            view.setTag(TAG_KEY_POSITION, position);
            tabsContainerView.addView(view);
        }
    }

    private final OnClickListener onTabClick = new OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            int position = (int) view.getTag(TAG_KEY_POSITION);
            if (notAlreadyAt(position)) {
                state.updateFastForwardPosition(position);
                forceDrawIndicatorAtPosition(position);
            }
            viewPager.setCurrentItem(position);
        }
    };

    public void attach(ViewPager viewPager) {
        this.viewPager = viewPager; // TODO: we want to remove this, and create a simple adapter that uses this to do the click tab to set page
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (tabsContainerView.getChildCount() == 0) {
            return;
        }

        drawIndicator(canvas, indicatorCoordinatesCalculator.calculate(state.getPosition(), state.getPagePositionOffset(), tabsContainerView));
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

    private boolean notAlreadyAt(int position) {
        return position != state.getPosition();
    }

    private void forceDrawIndicatorAtPosition(int position) {
        state.updatePosition(position);
        state.updatePositionOffset(0);
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

        protected abstract void bindView(T view, int position);

        void setListener(Listener<T> listener) {
            this.listener = listener;
        }

        protected void notifyDataSetChanged() {
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
