package com.ljt.rvanalysis.common.adapter;

/**
 * Created by lijiateng on 2019/4/2.
 *
 * 为 RecyclerView 添加多布局支持
 */

public interface MultiTypeSupport<Data> {

    int getLayoutId(Data data);

}
