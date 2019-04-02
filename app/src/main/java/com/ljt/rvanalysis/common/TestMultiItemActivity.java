package com.ljt.rvanalysis.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.common.adapter.MultiTypeSupport;

import java.util.ArrayList;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class TestMultiItemActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new TestMultiItemAdapter(this, new ArrayList<String>(), new MultiTypeSupport<String>() {
            @Override
            public int getLayoutId(String s) {

                // 根据数据的属性来决定加载哪种布局
                if (s == null) {
                    return R.layout.layout_basic_use_item;
                } else {
                    return R.layout.layout_test;
                }

            }
        });
    }
}
