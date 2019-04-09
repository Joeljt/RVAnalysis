package com.ljt.rvanalysis.load.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.ljt.rvanalysis.R;
import com.ljt.rvanalysis.load.joe_rv.RefreshViewHelper;

/**
 * Created by lijiateng on 2019/4/9.
 */

public class MyRvHelper extends RefreshViewHelper {

    // 加载数据的ImageView
    private View mRefreshIv;

    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        return refreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int totalHeight) {
//        mRefreshIv.setRotation(currentDragHeight/totalHeight * 360);

        // 就得用 float，否则四舍五入后会变成 0 或者 1，从而导致无效
        float rotate = ((float) currentDragHeight) / totalHeight;
        mRefreshIv.setRotation(rotate * 360);
    }

    @Override
    public void onRefreshing() {
        // 刷新过程中旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(700);
        rotateAnimation.setRepeatCount(-1);
        mRefreshIv.startAnimation(rotateAnimation);
    }

    @Override
    public void onStopRefreshing() {
        mRefreshIv.setRotation(0);
        mRefreshIv.clearAnimation();
    }

}
