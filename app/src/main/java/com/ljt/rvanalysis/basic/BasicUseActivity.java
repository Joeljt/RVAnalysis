package com.ljt.rvanalysis.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.decorations.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijiateng on 2019/3/20.
 */

public class BasicUseActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_use);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new RecyclerItemDecoration());
        mRv.setAdapter(new RecyclerAdapter(this, initRecyclerData()));

    }

    private List<String> initRecyclerData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

}
