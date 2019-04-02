package com.ljt.rvanalysis.common;

import android.content.Context;
import android.widget.ImageView;

import com.ljt.rvanalysis.common.adapter.CommonRvAdapter;
import com.ljt.rvanalysis.common.adapter.CommonViewHolder;
import com.ljt.rvanalysis.common.adapter.HolderImageLoader;

import java.util.List;

/**
 * Created by lijiateng on 2019/4/2.
 */

public class TestCommonAdapter extends CommonRvAdapter<String> {

    public TestCommonAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getItemLayoutId() {
        return 0;
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {
        holder.setImagePath(android.R.id.icon, new MyImageLoader(""));
    }

    class MyImageLoader extends HolderImageLoader {

        public MyImageLoader(String imgPath) {
            super(imgPath);
        }

        @Override
        public void loadImage(ImageView imageView, String path) {

            // 通过一层封装，使得不受限于某个特定的三方库，完全由调用者自己选择实现

            // Glide

            // Fresco

            // bala bala

        }
    }


}
