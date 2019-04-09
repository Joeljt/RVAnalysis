package com.ljt.rvanalysis.load.joe_rv;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ljt.rvanalysis.wrap.WrapRecyclerView;

/**
 * Created by lijiateng on 2019/4/9.
 * <p>
 * 支持下拉刷新的 RecyclerView，实现思路主要为：
 * 1. 给 RecyclerView 添加一个头部，默认隐藏
 * 2. 这个 RefreshLayout 跟随手指滑动展示出来，松手时可以执行刷新
 * 3. LoadingLayout 由于需要自定义，因此要给外部提供方法，只接受一个 View
 */

public class RefreshingRv extends WrapRecyclerView {

    // 下拉刷新的辅助类
    private RefreshViewHelper mRefreshViewHelper;

    // 下拉刷新的 View
    private View mRefreshView;

    // 下拉刷新 View 的高度
    private int mRefreshViewHeight;

    // 手指触摸屏幕的 Y 值
    private int mFingerDownY;

    // 手指拖动屏幕的阻尼系数
    private float mDragIndex = 0.65f;

    // 当前是否正在拖动
    private boolean mCurrentDragging = false;

    // 当前刷新状态
    private int mCurrRefreshStatus;

    // 默认状态
    private int REFRESH_STATUS_NORMAL = 0x0011;

    // 下拉刷新
    private int REFRESH_STATUS_PULL_TO_REFRESH = 0x0012;

    // 释放刷新
    private int REFRESH_STATUS_LOOSE_TO_REFRESH = 0x0013;

    // 正在刷新
    private int REFRESH_STATUS_REFRESHING = 0x0014;

    public RefreshingRv(Context context) {
        super(context);
    }

    public RefreshingRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshingRv(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 下拉刷新的样式不一定是固定的
     * 需要使用辅助类来进行区分，而不是直接 addHeaderView
     */
    public void addRefreshViewCreator(RefreshViewHelper refreshViewHelper) {
        this.mRefreshViewHelper = refreshViewHelper;
        addRefreshView();
    }

    /**
     * 添加 RefreshingLayout
     * 从 RefreshViewHelper 中获取
     */
    private void addRefreshView() {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && mRefreshViewHelper != null) {
            View refreshView = mRefreshViewHelper.getRefreshView(getContext(), this);
            if (refreshView != null) {
                this.mRefreshView = refreshView;
                addHeaderView(refreshView);
            }
        }
    }

    /**
     * 重写 onLayout 方法，默认将 refreshView 进行隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            if (mRefreshView != null && mRefreshViewHeight <= 0) {
                // 已经测量完毕了，获取 refreshView 的高度
                mRefreshViewHeight = mRefreshView.getMeasuredHeight();
                if (mRefreshViewHeight > 0) {
                    // 将整个 View 进行隐藏，移出屏幕外
                    resetRefreshViewPosition(-mRefreshViewHeight + 1);
                }
            }
        }

    }

    /**
     * 将 RefreshView 移除屏幕外，默认不显示刷新布局
     * <p>
     * 给 RefreshView 设置 LayoutParams 来实现
     * 为该 View 设置负的 marginTop
     *
     * @param refreshViewHeight
     */
    private void resetRefreshViewPosition(int refreshViewHeight) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) mRefreshView.getLayoutParams();
        if (refreshViewHeight < -mRefreshViewHeight + 1) {
            refreshViewHeight = -mRefreshViewHeight+1;
        }
        layoutParams.topMargin = refreshViewHeight;
        mRefreshView.setLayoutParams(layoutParams);
    }

    private void updateRefreshStatus(int refreshViewPosition) {
        if (refreshViewPosition <= -mRefreshViewHeight) {
            mCurrRefreshStatus = REFRESH_STATUS_NORMAL;
        } else if (refreshViewPosition < 0) {
            mCurrRefreshStatus = REFRESH_STATUS_PULL_TO_REFRESH;
        } else {
            mCurrRefreshStatus = REFRESH_STATUS_LOOSE_TO_REFRESH;
        }

        if (mRefreshViewHelper != null) {
            mRefreshViewHelper.onPull(refreshViewPosition, mRefreshViewHeight);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                // 之所以写在这里而不是 onTouchEvent
                // 因为如果我们处理了条目点击事件，那 onTouchEvent 将接收不到 down 事件
                this.mFingerDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentDragging) {
                    restoreRefreshView();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 抬起手指后，重置刷新状态
     */
    private void restoreRefreshView() {
        final int currentTopMargin = ((MarginLayoutParams) mRefreshView.getLayoutParams()).topMargin;
        int maxTopMargin = -mRefreshViewHeight + 1;
        if (mCurrRefreshStatus == REFRESH_STATUS_LOOSE_TO_REFRESH) {
            maxTopMargin = 0;
            mCurrRefreshStatus = REFRESH_STATUS_REFRESHING; // 松手后切换为正在刷新状态
            if (mRefreshViewHelper != null) {
                mRefreshViewHelper.onRefreshing();
            }

            if (onRefreshListener != null) {
                onRefreshListener.onRefresh();
            }

        }

        // 得到滑动出来的距离，将其重置
        int currDistance = currentTopMargin - maxTopMargin;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(currentTopMargin, maxTopMargin)
                .setDuration(currDistance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                resetRefreshViewPosition((int) value);
            }
        });
        valueAnimator.start();
        mCurrentDragging = false;

    }

    public void finishRefreshing() {
        mCurrRefreshStatus = REFRESH_STATUS_NORMAL;
        restoreRefreshView();
        if (mRefreshViewHelper != null) {
            mRefreshViewHelper.onStopRefreshing();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 如果是在最顶部才要将头部拉出来，否则不需要处理
                if (canScrollUp() || mCurrRefreshStatus == REFRESH_STATUS_REFRESHING) {
                    return super.onTouchEvent(e);
                }

                // 解决下拉刷新自动滚动问题
                if (mCurrentDragging) {
                    scrollToPosition(0);
                }

                // 获取手指触摸拖拽的距离
                int fingerMoveDistance = (int) ((e.getRawY() - mFingerDownY) * mDragIndex);
                if (fingerMoveDistance > 0) {
                    // 屏幕已经到顶，并且手指还在继续向下拉动
                    int marginTop = fingerMoveDistance - mRefreshViewHeight;
                    resetRefreshViewPosition(marginTop);
                    updateRefreshStatus(marginTop);
                    mCurrentDragging = true;
                    return false;
                }

                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     */
    public boolean canScrollUp() {
        return canScrollVertically(-1);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
