package com.ljt.rvanalysis.wrap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.RecyclerAdapter;
import com.ljt.rvanalysis.basic.decorations.FlexibleDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class WrapRVActivity extends AppCompatActivity {

    private WrapRecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_warp_rv);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
        mRv.addItemDecoration(new FlexibleDecoration(this, R.drawable.item_divider_linear));

        // 使用包裹的 adapter
        RecyclerAdapter mAdapter = new RecyclerAdapter(this, initRecyclerData());
        mRv.setAdapter(mAdapter);

        // addHeaderView
        View inflate = getLayoutInflater().inflate(R.layout.layout_basic_use_item, mRv, false);
        mRv.addHeaderView(inflate);
        mRv.addFooterView(getLayoutInflater().inflate(R.layout.layout_basic_use_item, mRv, false));

    }

    private List<String> initRecyclerData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("12");
        for (int i = 'a'; i <= 'z'; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

}
