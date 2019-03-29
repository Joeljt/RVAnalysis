package com.ljt.rvanalysis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lijiateng on 2019/3/29.
 */

public class TestActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_test);

        LinearLayout mParent = findViewById(R.id.ll_root);
        View inflateView = getLayoutInflater().inflate(R.layout.layout_basic_use_item, mParent, false);

        Log.e("Test", "LayoutParams -> " + inflateView.getLayoutParams());
        Log.e("Test", "Parent -> " + inflateView.getParent());

    }
}
