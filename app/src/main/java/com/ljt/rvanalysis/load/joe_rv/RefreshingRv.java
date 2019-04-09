package com.ljt.rvanalysis.load.joe_rv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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
            View refreshView = mRefreshViewHelper.getRefreshView();
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
                    resetRefreshViewPosition(mRefreshViewHeight);
                }
            }
        }

    }

    /**
     * 将 RefreshView 移除屏幕外，默认不显示刷新布局
     *
     * @param refreshViewHeight
     */
    private void resetRefreshViewPosition(int refreshViewHeight) {

    }
}
