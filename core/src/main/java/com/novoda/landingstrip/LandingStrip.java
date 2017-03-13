package com.novoda.landingstrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class LandingStrip extends HorizontalScrollView implements Scrollable, ViewPager.OnPageChangeListener {

    private final Attributes attributes;
    private final Paint indicatorPaint;
    private final State state;
    private final IndicatorCoordinatesCalculator indicatorCoordinatesCalculator;

    private TabsContainerView tabsContainerView;
    private ScrollingPageChangeListener scrollingPageChangeListener;
    private Notifier notifier;

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

        notifier = new Notifier(tabsContainerView);
    }

    public <T extends View> void setAdapter(LandingStripAdapter<T> adapter) {
        adapter.setListener(notifier);
        adapter.notifyDataSetChanged();
    }

    public void setCurrentItem(int position) {
        if (alreadyAt(position)) {
            return;
        }
        state.updateFastForwardPosition(position);
        forceDrawIndicatorAtPosition(position);
    }

    private boolean alreadyAt(int position) {
        return position != state.position();
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

        Coordinates indicatorCoordinates = calculateIndicatorCoordinates(state);
        drawIndicator(canvas, indicatorCoordinates);
    }

    protected Coordinates calculateIndicatorCoordinates(ReadOnlyState state) {
        return indicatorCoordinatesCalculator.calculate(
                state.position(),
                state.offset(),
                tabsContainerView
        );
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

}
