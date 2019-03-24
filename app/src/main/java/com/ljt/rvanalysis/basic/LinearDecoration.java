package com.ljt.rvanalysis.basic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lijiateng on 2019/3/24.
 */

public class LinearDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDivider;

    public LinearDecoration(Context context, int drawableResId) {
        mDivider = context.getDrawable(drawableResId);
    }



}
