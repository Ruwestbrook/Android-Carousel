package com.example.westbrook.bannerview.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by westbrook on 2017/6/4.
 */

public abstract class BannerAdapter{

    public abstract View getView(int position);
    public abstract int getCount();

    public String[] bannerAd() {
        return null;
    }
}
