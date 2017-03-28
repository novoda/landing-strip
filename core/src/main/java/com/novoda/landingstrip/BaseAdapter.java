package com.novoda.landingstrip;

import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter<T extends View> {

    private Listener<T> listener;

    protected abstract T createView(ViewGroup parent, int position);

    protected abstract void bindView(T view, int position);

    void setListener(Listener<T> listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        if (listener != null) {
            listener.onNotifyDataSetChanged(this);
        }
    }

    public void notifyItemChanged(int position) {
        if (listener != null) {
            listener.onNotifyItemChanged(this, position);
        }
    }

    protected abstract int getCount();

    interface Listener<T extends View> {

        void onNotifyDataSetChanged(BaseAdapter<T> adapter);

        void onNotifyItemChanged(BaseAdapter<T> adapter, int position);
    }
}
