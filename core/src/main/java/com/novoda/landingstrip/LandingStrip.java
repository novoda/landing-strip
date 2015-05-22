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
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class LandingStrip extends HorizontalScrollView implements Scrollable, OnPagerAdapterChangedListener {

    private static final int TAG_KEY_POSITION = R.id.ls__tag_key_position;

    private final Attributes attributes;
    private final LayoutInflater layoutInflater;
    private final Paint indicatorPaint;
    private final State state;
    private final IndicatorCoordinatesCalculator indicatorCoordinatesCalculator;
    private final PagerAdapterObserver pagerAdapterObserver;
    private final TabsContainer tabsContainer;

    private ViewPager viewPager;
    private TabSetterUpper tabSetterUpper;
    private OnPageChangedListenerCollection onPageChangeListenerCollection;

    public LandingStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFillViewport(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);

        this.attributes = Attributes.readAttributes(context, attrs);
        this.layoutInflater = LayoutInflater.from(context);
        this.indicatorPaint = new Paint();
        this.state = new State();
        this.indicatorCoordinatesCalculator = IndicatorCoordinatesCalculator.newInstance();
        this.pagerAdapterObserver = new PagerAdapterObserver(this);
        this.tabsContainer = TabsContainer.newInstance(context, attributes);
        this.onPageChangeListenerCollection = OnPageChangedListenerCollection.newInstance();

        state.updateDelegateOnPageListener(onPageChangeListenerCollection);
        state.updatePosition(0);
        state.updatePositionOffset(0);
        state.invalidateFastForwardPosition();

        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(getResources().getColor(attributes.getIndicatorColor()));

        tabsContainer.attachTo(this);
    }

    /**
     * Deprecated in favour of {@link #addOnPageChangeListener(ViewPager.OnPageChangeListener)}.
     * <p/>
     * Using this method in combination with {@link #addOnPageChangeListener(ViewPager.OnPageChangeListener)} has unguarded consequences.
     *
     * @param onPageChangeListener
     */
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        state.updateDelegateOnPageListener(onPageChangeListener);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        onPageChangeListenerCollection.add(onPageChangeListener);
        state.updateDelegateOnPageListener(onPageChangeListenerCollection);
    }

    public void attach(ViewPager viewPager) {
        attach(viewPager, viewPager.getAdapter());
    }

    public void attach(ViewPager viewPager, PagerAdapter pagerAdapter) {
        attach(viewPager, pagerAdapter, SIMPLE_TEXT_TAB_SETTER_UPPER);
    }

    private static final TabSetterUpper SIMPLE_TEXT_TAB_SETTER_UPPER = new TabSetterUpper() {
        @Override
        public View setUp(int position, CharSequence title, View inflatedTab) {
            ((TextView) inflatedTab).setText(title);
            inflatedTab.setFocusable(true);
            return inflatedTab;
        }
    };

    public void attach(ViewPager viewPager, PagerAdapter pagerAdapter, TabSetterUpper tabSetterUpper) {
        this.viewPager = viewPager;
        this.tabSetterUpper = tabSetterUpper;

        pagerAdapterObserver.registerTo(pagerAdapter);
        pagerAdapterObserver.onChanged();
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

    @Override
    public void onPagerAdapterChanged(PagerAdapter pagerAdapter) {
        notifyDataSetChanged(pagerAdapter);
    }

    private void notifyDataSetChanged(PagerAdapter pagerAdapter) {
        tabsContainer.clearTabs();
        int tabCount = pagerAdapter.getCount();
        for (int position = 0; position < tabCount; position++) {
            CharSequence title = pagerAdapter.getPageTitle(position);
            View inflatedTabView = tabsContainer.inflateTab(layoutInflater, attributes.getTabLayoutId());
            addTab(position, title, inflatedTabView, tabSetterUpper);
        }
        tabsContainer.startWatching(viewPager, new ScrollingPageChangeListener(state, tabsContainer, this));
    }

    private void addTab(final int position, CharSequence title, View tabView, TabSetterUpper tabSetterUpper) {
        tabView = tabSetterUpper.setUp(position, title, tabView);
        tabsContainer.addTab(tabView, position);
        tabView.setOnClickListener(onTabClick);
        tabView.setTag(TAG_KEY_POSITION, position);
    }

    private final OnClickListener onTabClick = new OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            int position = (int) view.getTag(TAG_KEY_POSITION);
            state.updateFastForwardPosition(position);
            viewPager.setCurrentItem(position);
        }
    };

    public interface TabSetterUpper {
        View setUp(int position, CharSequence title, View inflatedTab);
    }

}
