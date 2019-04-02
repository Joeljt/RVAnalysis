package com.ljt.rvanalysis.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lijiateng on 2019/4/2.
 *
 * 通用的 RecyclerView ViewHolder
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View findView = mViews.get(viewId);
        if (findView == null) {
            findView = itemView.findViewById(viewId);
            mViews.put(viewId, findView);
        }
        return (T) findView;
    }

    public CommonViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public CommonViewHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public CommonViewHolder setTextSize(int viewId, int sizeInSp) {
        TextView textView = getView(viewId);
        textView.setTextSize(sizeInSp, TypedValue.COMPLEX_UNIT_SP);
        return this;
    }

    public CommonViewHolder setImagePath(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        imageLoader.loadImage(imageView, imageLoader.getPath());
        return this;
    }

}
