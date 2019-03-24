package com.ljt.rvanalysis.basic.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ljt.rvanalysis.R;

/**
 * Created by lijiateng on 2019/3/24.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public GridItemDecoration(Context context, int drawableResId) {
        mDivider = ContextCompat.getDrawable(context, drawableResId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.right = mDivider.getIntrinsicWidth();
        outRect.bottom = mDivider.getIntrinsicHeight();

        if (parent.getLayoutManager() instanceof GridLayoutManager) {

            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);
            if (position % spanCount == (spanCount - 1)) {
                outRect.right = 0;
            }
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams rvLayoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - rvLayoutParams.topMargin;
            int bottom = childView.getBottom() + rvLayoutParams.bottomMargin;
            int left = childView.getRight() + rvLayoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                int spanCount = layoutManager.getSpanCount();
                if (i >= childCount - spanCount) {
                    break;
                }
            }

            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams rvLayoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - rvLayoutParams.leftMargin;
            int right = childView.getRight() + mDivider.getIntrinsicWidth() + rvLayoutParams.rightMargin;
            int top = childView.getBottom() + rvLayoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }

}
