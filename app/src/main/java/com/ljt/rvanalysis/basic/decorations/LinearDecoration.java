package com.ljt.rvanalysis.basic.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lijiateng on 2019/3/24.
 */

public class LinearDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDivider;

    public LinearDecoration(Context context, int drawableResId) {
        mDivider = context.getDrawable(drawableResId);
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

//        c.drawBitmap();

    }
}
