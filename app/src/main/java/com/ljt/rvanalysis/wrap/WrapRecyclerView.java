package com.ljt.rvanalysis.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mAdapter;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (adapter instanceof WrapRecyclerAdapter) {
            mAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mAdapter = new WrapRecyclerAdapter(adapter);

            // 使用观察者模式解决两个 adapter 在添加或移除数据时，界面无法统一的问题
            adapter.registerAdapterDataObserver(new AdapterDataObserver() {

                @Override
                public void onChanged() {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mAdapter.notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        super.setAdapter(mAdapter);
    }

    public void addHeaderView(View view) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (mAdapter != null) {
            mAdapter.addFooterView(view);
        }
    }

}
