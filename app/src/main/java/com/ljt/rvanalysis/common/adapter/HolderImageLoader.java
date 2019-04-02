package com.ljt.rvanalysis.common.adapter;

import android.widget.ImageView;

/**
 * Created by lijiateng on 2019/4/2.
 */

public abstract class HolderImageLoader {

    private String mPath;

    public HolderImageLoader(String imgPath) {
        this.mPath = imgPath;
    }

    public abstract void loadImage(ImageView imageView, String path);

    public String getPath() {
        return mPath;
    }

}
