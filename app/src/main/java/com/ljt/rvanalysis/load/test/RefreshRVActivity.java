package com.ljt.rvanalysis.load.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.RecyclerAdapter;
import com.ljt.rvanalysis.load.darren_rv.RefreshRecyclerView;
import com.ljt.rvanalysis.load.joe_rv.RefreshingRv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class RefreshRVActivity extends AppCompatActivity {

    private RefreshingRv mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_refresh_rv);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new LinearLayoutManager(this));
//        mRv.addItemDecoration(new FlexibleDecoration(this, R.drawable.item_divider_linear));

        // 使用包裹的 adapter
        RecyclerAdapter mAdapter = new RecyclerAdapter(this, initRecyclerData());
        mRv.setAdapter(mAdapter);

        mRv.addRefreshViewCreator(new MyRvHelper());

        final Handler handler = new Handler();
        mRv.setOnRefreshListener(new RefreshingRv.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRv.finishRefreshing();
                    }
                }, 2000);
            }
        });

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
