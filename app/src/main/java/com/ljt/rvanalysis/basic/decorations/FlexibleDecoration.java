package com.ljt.rvanalysis.basic.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ljt.rvanalysis.R;

/**
 * Created by lijiateng on 2019/3/24.
 */

public class FlexibleDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public FlexibleDecoration(Context context, int drawableResId) {
        mDivider = ContextCompat.getDrawable(context, drawableResId);
    }

    /**
     * 获取 item 偏移
     * <p>
     * 1. ListView 除第一个 item 之外，其他的 item 都在 top 的位置偏移
     * 2. GridView 每个条目都在 right 以及 bottom 有偏移
     * 但是需要注意的是，最靠边的位置，right 没有偏移；最后一行的 bottom 也不应该有偏移
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        // since GridLayoutManager extends LinearLayoutManager
        // so you have to check GridLayoutManager first
        if (layoutManager instanceof GridLayoutManager) {
            getItemOffSetsForGridView(outRect, view, parent);
            return;
        }

        if (layoutManager instanceof LinearLayoutManager) {
            getItemOffSetsForListView(outRect, view, parent);
        }


    }

    /**
     * 为 GridView 样式设置 item 偏移
     * <p>
     * GridView 每个条目都在 right 以及 bottom 有偏移
     * 但是需要注意的是，最靠边的位置，right 没有偏移；最后一行的 bottom 也不应该有偏移
     */
    private void getItemOffSetsForGridView(Rect outRect, View view, RecyclerView parent) {
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();

        // 如果是最后一列，right 无偏移
        if (isLastColumn(view, parent)) {
            right = 0;
        }

        // 如果是最后一行，bottom 无偏移
        if (isLastRow(view, parent)) {
            bottom = 0;
        }

        outRect.right = right;
        outRect.bottom = bottom;

    }

    /**
     * 判断当前是否属于最后一行
     *
     * @return （当前位置 + 1）是否已经到达最后一行，即大于（总条目数 - 列数）
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        return (itemPosition + 1) > (parent.getAdapter().getItemCount() - layoutManager.getSpanCount());
    }

    private boolean isLastRowByDivide(View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();

        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount : (parent.getAdapter().getItemCount() / spanCount + 1);

        boolean b = itemPosition + 1 > (rowNumber - 1) * spanCount;

        return b;
    }

    /**
     * 判断当前是否属于最后一列
     *
     * @return （当前位置 + 1）是否能整除 GridLayoutManager 的列数
     */
    private boolean isLastColumn(View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        return (itemPosition + 1) % layoutManager.getSpanCount() == 0;
    }

    /**
     * 为 ListView 样式设置 item 偏移
     * <p>
     * ListView 除第一个 item 之外，其他的 item 都在 top 的位置偏移
     */
    private void getItemOffSetsForListView(Rect outRect, View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        // since GridLayoutManager extends LinearLayoutManager
        // so you have to check GridLayoutManager first
        if (layoutManager instanceof GridLayoutManager) {
            drawDividerForGridView(c, parent);
            return;
        }

        if (layoutManager instanceof LinearLayoutManager) {
            drawDividerForListView(c, parent);
        }

    }

    /**
     * 为 GridView 绘制分割线
     */
    private void drawDividerForGridView(Canvas canvas, RecyclerView parent) {

        // 绘制横向分割线
        drawHorizontalGrid(canvas, parent);

        // 绘制纵向分割线
        drawVerticalGrid(canvas, parent);

    }

    private void drawVerticalGrid(Canvas canvas, RecyclerView parent) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

//            if (isLastColumn(view, parent)) {
//                continue;
//            }

            int left = view.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = view.getTop() - params.topMargin;
            int bottom = view.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

    }

    private void drawHorizontalGrid(Canvas canvas, RecyclerView parent) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

//            if (isLastRow(view, parent)) {
//                break;
//            }

            int left = view.getLeft() - params.leftMargin;
            int right = view.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = view.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

    }

    /**
     * 为 ListView 绘制分割线
     */
    private void drawDividerForListView(Canvas canvas, RecyclerView parent) {

        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {

            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            rect.top = view.getBottom() + params.bottomMargin;
            rect.bottom = rect.top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(rect);
            mDivider.draw(canvas);
        }

    }

}
