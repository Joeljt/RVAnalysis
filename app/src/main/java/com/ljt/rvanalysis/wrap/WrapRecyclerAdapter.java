package com.ljt.rvanalysis.wrap;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lijiateng on 2019/4/2.
 * <p>
 * 原始 Adapter 的包裹类，用于实现 addHeaderView 与 addFooterView
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 原始的数据 adapter，不包括头部和底部
    private RecyclerView.Adapter mAdapter;

    // 头部和底部的集合
    // 最初考虑用 ArrayList 实现，但是链表无法严格区分多个头部和多个底部的情况
    // 所以需要使用 map 集合来实现，使用 viewId 来区分每个不同的布局
    private SparseArray<View> mHeaders;
    private SparseArray<View> mFooters;

    private int mHeaderKey = 100000;
    private int mFooterKey = 200000;

    public WrapRecyclerAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        mHeaders = new SparseArray<>();
        mFooters = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 头部
        if (mHeaders.indexOfKey(viewType) >= 0) {
            return onCreateHeaderFooterViewHolder(mHeaders.get(viewType));
        }

        // 底部
        if (mFooters.indexOfKey(viewType) >= 0) {
            return onCreateHeaderFooterViewHolder(mFooters.get(viewType));
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 创建头部和底部的 ViewHolder
     *
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder onCreateHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // 头部和底部不需要绑定数据，直接把调整好的 position 位置传递出去
        if (mAdapter != null && position >= getHeadersCount()) {
            int adjPosition = position - getHeadersCount();
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {

        // 如果当前位置比头部数量还少，那当前的 ViewType 就应该是对应的头部信息
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaders.keyAt(position);
        }

        // 如果当前位置超过了头部数量，那就到了数据条目
        // 调整一下对应的 position 位置，即 当前位置 - 头部数量
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        // 底部
        int footerPos = 0;
        if (mAdapter != null) {
            footerPos = position - numHeaders - mAdapter.getItemCount();
        }

        return mFooters.keyAt(footerPos);
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    public void addHeaderView(View view) {
        int indexOfValue = mHeaders.indexOfValue(view);
        if (indexOfValue == -1) {
            mHeaders.put(mHeaderKey++, view);
        }
    }

    public void addFooterView(View view) {
        int indexOfValue = mFooters.indexOfValue(view);
        if (indexOfValue == -1) {
            mFooters.put(mFooterKey++, view);
        }
    }

    public void removeHeaderView(View view) {
        int indexOfValue = mHeaders.indexOfValue(view);
        if (indexOfValue > -1) {
            mHeaders.removeAt(indexOfValue);
        }
    }

    public void removeFooterView(View view) {
        int indexOfValue = mFooters.indexOfValue(view);
        if (indexOfValue > -1) {
            mFooters.removeAt(indexOfValue);
        }
    }

    public int getHeadersCount() {
        return mHeaders.size();
    }

    public int getFootersCount() {
        return mFooters.size();
    }
}
