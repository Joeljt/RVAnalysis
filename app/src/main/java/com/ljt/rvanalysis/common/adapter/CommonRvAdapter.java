package com.ljt.rvanalysis.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by lijiateng on 2019/4/2.
 */

public abstract class CommonRvAdapter<Data> extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    /**
     * 参数通用就只能用泛型来处理
     */
    private List<Data> mData;

    private LayoutInflater mInflater;
    private MultiTypeSupport mTypeSupport;

    public CommonRvAdapter(Context context, List<Data> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(mContext); // 一次初始化，避免在 onCreateViewHolder 时重复调用
    }

    public CommonRvAdapter(Context context, List<Data> data, MultiTypeSupport<Data> multiTypeSupport) {
        this(context, data);
        mTypeSupport = multiTypeSupport;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = getItemLayoutId();

        if (mTypeSupport != null) {
            layoutId = viewType;
        }

        View itemLayout = mInflater.inflate(layoutId, parent, false);
        return new CommonViewHolder(itemLayout);
    }

    @Override
    public int getItemViewType(int position) {

        if (mTypeSupport != null) {

            // 构造器中需要传入 MultiTypeSupport 对象
            // 在那时候会调用 getLayoutId 方法
            // 该方法返回的就是 layoutId，所以此处直接当做 viewType 返回即可
            // 在 onCreateViewHolder 方法中，会直接将 viewType 当做 layoutId 赋值
            return mTypeSupport.getLayoutId(mData.get(position));
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mData.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public abstract int getItemLayoutId();
    public abstract void convert(CommonViewHolder holder, Data data, int position);

}
