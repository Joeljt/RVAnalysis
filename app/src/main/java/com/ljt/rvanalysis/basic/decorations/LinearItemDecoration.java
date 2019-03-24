package com.ljt.rvanalysis.basic.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lijiateng on 2019/3/24.
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDivider;

    public LinearItemDecoration(Context context, int drawableResId) {
        mDivider = ContextCompat.getDrawable(context, drawableResId);
    }

    /**
     * 为所有 item 设置 offset 偏移，默认为 0
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        // 除了第一个 item，其他的全部在顶部偏移 10 个像素
        if (position != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {
            rect.top = parent.getChildAt(i).getBottom();
            rect.bottom = rect.top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(rect); // 给 drawable 对象设置边界，用调用 canvas 完成绘制
            mDivider.draw(c);
        }

    }
}
