package com.ljt.rvanalysis.load.darren_rv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lijiateng on 2019/4/3.
 *
 * 下拉刷新的辅助类：为了匹配所有效果
 *
 */

public abstract class RefreshViewCreator {

    /**
     * 获取下拉刷新的 View
     *
     * @param context
     * @param parent
     * @return 要在刷新位置展示的 RefreshView
     */
    public abstract View getRefreshView(Context context, ViewGroup parent);

    /**
     * 正在下拉
     *
     * @param currentDragHeight 当前拖动的距离
     * @param refreshViewHeight 总的刷新高度
     * @param currentRefreshStatus 刷新状态
     */
    public abstract void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);

    /**
     * 正在刷新中
     */
    public abstract void onRefreshing();

    /**
     * 停止刷新
     */
    public abstract void onStopRefresh();

}
