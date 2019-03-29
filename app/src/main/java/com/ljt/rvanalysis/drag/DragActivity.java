package com.ljt.rvanalysis.drag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.basic.RecyclerAdapter;
import com.ljt.rvanalysis.basic.decorations.FlexibleDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lijiateng on 2019/3/20.
 */

public class DragActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private RecyclerAdapter mAdapter;
    private List<String> mItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_use);

        mRv = findViewById(R.id.recycler_view);
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRv.addItemDecoration(new FlexibleDecoration(this, R.drawable.item_divider_linear));

        mItems = initRecyclerData();
        mAdapter = new RecyclerAdapter(this, mItems);
        mRv.setAdapter(mAdapter);

        setupItemTouchHelper();

    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int moveFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(moveFlag, 0);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(0);
                viewHolder.itemView.setTranslationX(0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();

                if (fromPosition > targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(mItems, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mItems, i, i - 1);// 改变实际的数据集
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, targetPosition);

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

        });

        itemTouchHelper.attachToRecyclerView(mRv);

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
