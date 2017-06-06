package com.example.westbrook.bannerview.Adapter;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


/**
 * Created by westbrook on 2017/6/4.
 */

public class MyScroller extends Scroller {


    //duration  持续时间  表示切换效果的持续时间
    private int mScrollerDuration=750;
    public MyScroller(Context context) {
        super(context);
    }
    public void setDuration(int duration) {
        this.mScrollerDuration = duration;
    }
    public MyScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public MyScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy,int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
