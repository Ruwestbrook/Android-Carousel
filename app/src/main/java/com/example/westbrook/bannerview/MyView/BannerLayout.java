package com.example.westbrook.bannerview.MyView;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.westbrook.bannerview.Adapter.BannerAdapter;
import com.example.westbrook.bannerview.MainActivity;
import com.example.westbrook.bannerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by westbrook on 2017/6/4.
 */

public class BannerLayout extends RelativeLayout {
    //轮播图
    BannerView mBannerView;
    //广告
    TextView mTextView;
    //点
    LinearLayout mLayout;
    //轮播图的适配器
    BannerAdapter mAdapter;
    //背景
    RelativeLayout mRelativeLayout;
    private Context mContext;
    //选中的点
    private Drawable mDotIndicatorFocusDrawable;
    //没有选中的点
    private Drawable mDotIndicatorNormalDrawable;
    //点的集合
    private List<DotIndicatorView> mDotIndicatorList=new ArrayList<>();
    //图片数量
    int count;
    private int mDotIndicatorGravity=-1;
    private int mDotIndicatorSize=8;
    private int mDotIndicatorDistance=10;
    private int backgroundColor=R.color.dark;
    private int rollSpeed;
    private int duration;
    private int width;
    private int mWidthProportion,mHeightProportion;
    public BannerLayout(Context context) {
        this(context,null);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
        initAttribute(attrs);
    }
    private void initView() {
        inflate(mContext, R.layout.banner_layout,this);
        //轮播图
        mBannerView= (BannerView) findViewById(R.id.banner);
        //广告
        mTextView= (TextView) findViewById(R.id.banner_ad);
        //点
        mLayout= (LinearLayout) findViewById(R.id.banner_dian);
        //背景
        mRelativeLayout= (RelativeLayout) findViewById(R.id.background);

    }
    private void initAttribute(AttributeSet attrs) {
        //获取自定义属性
       TypedArray array= mContext.obtainStyledAttributes(attrs,R.styleable.BannerLayout);
        //位置 -1 =right 0 =center 1=left
        mDotIndicatorGravity=array.getInt(R.styleable.BannerLayout_dotGravity,-1);
        //间距
        mDotIndicatorDistance= (int) array.getDimension(R.styleable.BannerLayout_dotDistance,dip2px(10));
        //大小   已经转化成px了
        mDotIndicatorSize= (int) array.getDimension(R.styleable.BannerLayout_dotSize,dip2px(8));
        //未选中的点的颜色
        mDotIndicatorNormalDrawable=array.getDrawable(R.styleable.BannerLayout_dotIndicatorNormal);
        if(mDotIndicatorNormalDrawable==null){
            mDotIndicatorNormalDrawable=new ColorDrawable(Color.RED);
        }
        //选中的点的颜色
        mDotIndicatorFocusDrawable=array.getDrawable(R.styleable.BannerLayout_dotIndicatorFocus);
        if(mDotIndicatorFocusDrawable==null){
            mDotIndicatorFocusDrawable=new ColorDrawable(Color.WHITE);
        }
        //背景颜色
        backgroundColor=array.getColor(R.styleable.BannerLayout_backgroundColor,Color.TRANSPARENT);
        //切换时间
        rollSpeed=array.getInt(R.styleable.BannerLayout_rollSpeed,3000);
        duration=array.getInt(R.styleable.BannerLayout_duration,2000);
        mWidthProportion=array.getInt(R.styleable.BannerLayout_widthProportion,5);
        mHeightProportion=array.getInt(R.styleable.BannerLayout_heightProportion,2);
        //回收
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         width=getMeasuredWidth();
        if(mWidthProportion==0 || mHeightProportion==0){
            return;
        }
        int height=width*mHeightProportion/mWidthProportion;
        getLayoutParams().height=height;
    }

    public void setBannerAdapter(BannerAdapter adapter){
            mAdapter=adapter;
            mBannerView.setAdapter(adapter);
            mBannerView.StartOnRoll(rollSpeed);//切换时间
            mBannerView.setDuration(duration);//动画持续时间;
            initDotIndicator();
        //设置监听事件 让页面转换的时候  点也跟着变
        mBannerView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (DotIndicatorView dotIndicatorView : mDotIndicatorList) {
                    dotIndicatorView.setDrawable(mDotIndicatorNormalDrawable);
                }
                mDotIndicatorList.get(position%count).setDrawable(mDotIndicatorFocusDrawable);
                if(mAdapter.bannerAd()!=null)
                mTextView.setText(mAdapter.bannerAd()[position%count]);
                mBannerView.mHandler.removeMessages(mBannerView.MESSAGE_ID);
                mBannerView.StartOnRoll(mBannerView.MESSAGE_TIME);
                Log.d("TEST",String.valueOf(((Activity)getContext())instanceof MainActivity));
            }
        });


    }
       void initDotIndicator(){
            setMyGravity();
           mRelativeLayout.setBackgroundColor(backgroundColor);
            count=mAdapter.getCount();
           for (int i = 0; i < count; i++) {
                final int index=i;
               //像mLinear中添加圆点
               DotIndicatorView view=new DotIndicatorView(mContext);
               //设置View的大小
               Log.d("TEST","大小"+mDotIndicatorSize);
               LinearLayout.LayoutParams lp=new LinearLayout.
                       LayoutParams(mDotIndicatorSize,mDotIndicatorSize);
               lp.leftMargin=lp.rightMargin=mDotIndicatorDistance;
               view.setLayoutParams(lp);
               //选中的应该用draw able
               if(i==0)
                   view.setDrawable(mDotIndicatorFocusDrawable);
               else
               view.setDrawable(mDotIndicatorNormalDrawable);
               mDotIndicatorList.add(view);
               mLayout.addView(view);
           }
           if(mAdapter.bannerAd()==null)
               mTextView.setVisibility(GONE);
           else
               mTextView.setText(mAdapter.bannerAd()[0]);

       }


       /*
       把dip转px;
        */
    private int dip2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                i,getResources().getDisplayMetrics());
    }
    void setMyGravity(){
        switch (mDotIndicatorGravity){
            case -1:
                mLayout.setGravity(Gravity.RIGHT);
                break;
            case 0:
                mLayout.setGravity(Gravity.CENTER);
                break;
            case 1:
                mLayout.setGravity(Gravity.LEFT);
                break;
        }
    }

}
