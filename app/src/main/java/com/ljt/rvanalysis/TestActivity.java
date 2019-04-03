package com.ljt.rvanalysis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

        LinearLayout linearLayout = new LinearLayout(this);




        View.inflate(this, R.layout.layout_basic_use_item, null);



        LayoutInflater.from(this).inflate(R.layout.layout_basic_use_item, linearLayout, false);


        View.inflate(this, R.layout.layout_basic_use_item, linearLayout);
        LayoutInflater.from(this).inflate(R.layout.layout_basic_use_item, linearLayout, true);
        LayoutInflater.from(this).inflate(R.layout.layout_basic_use_item, linearLayout, false);


        LinearLayout mParent = findViewById(R.id.ll_root);
        View inflateView = View.inflate(this, R.layout.layout_basic_use_item, mParent);

        Log.e("Test", "LayoutParams -> " + inflateView.getLayoutParams());
        Log.e("Test", "Parent -> " + inflateView.getParent());

    }
}
