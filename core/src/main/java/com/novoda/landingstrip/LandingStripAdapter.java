package com.novoda.landingstrip;

import android.view.View;
import android.view.ViewGroup;

public abstract class LandingStripAdapter<T extends View> {

    private Listener listener;

    protected abstract T createView(ViewGroup parent, int position);

    protected abstract void bindView(T view, int position);

    void setListener(Listener listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        if (listener != null) {
            listener.onNotifyDataSetChanged(this);
        }
    }

    protected abstract int getCount();

    interface Listener {

        <T extends View> void onNotifyDataSetChanged(LandingStripAdapter<T> adapter);

    }
}
