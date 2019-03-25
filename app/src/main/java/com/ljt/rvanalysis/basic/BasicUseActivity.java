package com.ljt.rvanalysis.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.decorations.FlexibleDecoration;
import com.ljt.rvanalysis.basic.decorations.GridItemDecoration;
import com.ljt.rvanalysis.basic.decorations.LinearItemDecoration;
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
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRv.addItemDecoration(new FlexibleDecoration(this, R.drawable.item_divider_linear));
        mRv.setAdapter(new RecyclerAdapter(this, initRecyclerData()));

    }

    private List<String> initRecyclerData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("12");
        for (int i = 'a'; i <= 'z'; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rv_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear:
                mRv.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.grid:
                mRv.setLayoutManager(new GridLayoutManager(this, 3));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
