package com.ljt.rvanalysis.hf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.RecyclerAdapter;
import com.ljt.rvanalysis.basic.decorations.FlexibleDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class HeaderFooterActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_header_footer);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new LinearLayoutManager(this));
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

}
