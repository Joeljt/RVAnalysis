package com.ljt.rvanalysis.load.joe_rv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lijiateng on 2019/4/9.
 *
 * 下拉刷新的辅助类，为了匹配所有的效果
 *
 */

public abstract class RefreshViewHelper {

    /**
     * 获取下拉刷新的 View，准备添加到头部位置
     *
     * @param context 加载布局文件需要上下文环境
     * @param parent 为加载的 layout 布局生成 params
     * @return
     */
    public abstract View getRefreshView(Context context, ViewGroup parent);

    /**
     * 手指下拉的过程
     */
    public abstract void onPull(int currentDragHeight, int totalHeight);

    public abstract void onRefreshing();

    public abstract void onStopRefreshing();

}
