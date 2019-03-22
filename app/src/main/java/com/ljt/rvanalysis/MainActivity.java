package com.ljt.rvanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ljt.rvanalysis.basic.BasicUseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 基本使用
     * @param view
     */
    public void basicUse(View view) {
        Intent intent = new Intent(this, BasicUseActivity.class);
        startActivity(intent);
    }

}
