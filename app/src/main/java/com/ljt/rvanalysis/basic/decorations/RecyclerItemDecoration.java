package com.ljt.rvanalysis.basic.decorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lijiateng on 2019/3/20.
 */

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    public RecyclerItemDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemPosition = parent.getChildAdapterPosition(view);

        // parent.getChildCount() 是不断变化的，不可靠
        // 无法控制尾部，那就控制每个 item 的头部；设置除了第一个 item 以外，所有 item 都向下偏移 10 个像素
        if (itemPosition != 0) {
            outRect.top = 10;
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

//        Rect rect = new Rect();
//        rect.left = parent.getPaddingLeft();
//        rect.right = parent.getMeasuredWidth() - parent.getPaddingRight();
//
//        rect.bottom = 10;
//
//        int itemCount = parent.getAdapter().getItemCount();
//        for (int i = 0; i < itemCount; i++) {
//            View child = parent.getChildAt(i);
//            rect.top = child.getTop();
//            c.drawRect(rect, mPaint);
//        }


        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getMeasuredWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            rect.top = childView.getBottom(); // 分割线的 top，应该是前一个 item 的 bottom
            rect.bottom = rect.top + 10;
            c.drawRect(rect, mPaint);

//            rect.bottom = parent.getChildAt(i).getTop();
//            rect.top = rect.bottom - 10;
//            c.drawRect(rect, mPaint);

        }


    }

}
