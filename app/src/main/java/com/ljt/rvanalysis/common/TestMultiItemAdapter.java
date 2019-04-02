package com.ljt.rvanalysis.common;

import android.content.Context;

import com.ljt.rvanalysis.common.adapter.CommonRvAdapter;
import com.ljt.rvanalysis.common.adapter.CommonViewHolder;
import com.ljt.rvanalysis.common.adapter.MultiTypeSupport;

import java.util.List;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class TestMultiItemAdapter extends CommonRvAdapter<String> {

    public TestMultiItemAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    public TestMultiItemAdapter(Context context, List<String> strings, MultiTypeSupport<String> multiTypeSupport) {
        super(context, strings, multiTypeSupport);
    }

    @Override
    public int getItemLayoutId() {
        return 0;
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {

    }

}
