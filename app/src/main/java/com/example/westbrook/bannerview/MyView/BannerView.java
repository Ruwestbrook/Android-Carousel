package com.example.westbrook.bannerview.MyView;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.westbrook.bannerview.Adapter.BannerAdapter;
import com.example.westbrook.bannerview.Adapter.MyScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by westbrook on 2017/6/4.
 */

public class BannerView extends ViewPager {
    private static final String TAG = "BannerView";
    //BannerView自己的适配器
    private BannerAdapter mAdapter;
    //消息的ID
    public static final  int MESSAGE_ID=0x0010;
    public   int MESSAGE_TIME=2000;
    private MyScroller mScroller;
    private int count;
    public BannerView(Context context) {
        super(context,null);
    }
  public  Handler mHandler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          //轮播
          setCurrentItem(getCurrentItem()+1);
//          //轮询
//          StartOnRoll(MESSAGE_TIME);
      }
  };
    public void setAdapter(BannerAdapter adapter) {
        //给自定义的ViewPage设置适配器
        mAdapter=adapter;
        count=mAdapter.getCount();
        //给父类的ViewPage设置的适配器
        setAdapter(new BannerPageAdapter());
        setCurrentItem(232792560);
        ((Activity)getContext()).getApplication().registerActivityLifecycleCallbacks(mCallbacks);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //改变速率 就是改变Scroller中StartScroller中duration的值
        //但在ViewPage中 Scroller是私有的，所以要通过反射去设置
        try {
            mScroller=new MyScroller(context);
            Field field= ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            //第一个参数表示在那个类中，第二个参数是具体的值;
            field.set(this,mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setDuration(int duration){
        mScroller.setDuration(duration);
    }
    public void StartOnRoll(int time){
        //清楚之前的消息
        mHandler.removeMessages(MESSAGE_ID);
        //每隔一段时间发送消息
        MESSAGE_TIME=time;
        mHandler.sendEmptyMessageDelayed(MESSAGE_ID,time);
    }
   public void StartOnRoll(){
        //清楚之前的消息
        mHandler.removeMessages(MESSAGE_ID);
        //每隔一段时间发送消息
        mHandler.sendEmptyMessageDelayed(MESSAGE_ID,MESSAGE_TIME);
    }
    private class BannerPageAdapter extends PagerAdapter {
        private List<View> viewList=new ArrayList<>();

        @Override
        public int getCount() {
           return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //新建一个View
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=mAdapter.getView(position%count);
            //viewList.add(view);
            container.addView(view);
            return view;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        //销毁View
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
            object=null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(MESSAGE_ID);
        mHandler=null;
        //解除绑定
        ((Activity)getContext()).getApplication().unregisterActivityLifecycleCallbacks(mCallbacks);
    }
    //轮播图需要跟随activity生命周期变化
    public Application.ActivityLifecycleCallbacks mCallbacks=new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
                //开启轮播
            if(activity==(Activity) getContext())
          mHandler.sendEmptyMessageAtTime(MESSAGE_ID,MESSAGE_TIME);
        }

        @Override
        public void onActivityPaused(Activity activity) {
                //关闭轮播
            if(activity==(Activity) getContext())
            mHandler.removeMessages(MESSAGE_ID);
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
