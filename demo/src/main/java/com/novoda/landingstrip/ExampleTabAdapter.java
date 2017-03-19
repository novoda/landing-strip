package com.novoda.landingstrip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.novoda.landing_strip.R;
import com.novoda.landingstrip.setup.Data;

public class ExampleTabAdapter extends LandingStripAdapter<TextView> {

    private final LayoutInflater layoutInflater;
    private final OnTabClickListener tabClickListener;
    private final Data[] data;

    public ExampleTabAdapter(LayoutInflater layoutInflater, OnTabClickListener tabClickListener, Data[] data) {
        this.data = data;
        this.layoutInflater = layoutInflater;
        this.tabClickListener = tabClickListener;
    }

    @Override
    protected TextView createView(ViewGroup parent, int position) {
        return (TextView) layoutInflater.inflate(R.layout.tab_simple_text, parent, false);
    }

    @Override
    protected void bindView(TextView view, final int position) {
        final Data itemData = data[position];
        view.setText(itemData.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabClickListener.onTabClicked(position, itemData);
            }
        });
    }

    @Override
    protected int getCount() {
        return data.length;
    }

    interface OnTabClickListener {
        void onTabClicked(int position, Data data);
    }
}
