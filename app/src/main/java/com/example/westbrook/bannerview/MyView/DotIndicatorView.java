package com.example.westbrook.bannerview.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by westbrook on 2017/6/5.
 */

public class DotIndicatorView extends View {
    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context,null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mDrawable!=null){
//            //前两个是组件左上角在容器中的坐标
//            //后两个是组件的宽度和高度
//            mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
//            //把这个drawable画上去
//            mDrawable.draw(canvas);
            // 画圆
            Bitmap bitmap = drawableToBitmap(mDrawable);
            // 把Bitmap变为圆的
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            // 把圆形的Bitmap绘制到画布上
            canvas.drawBitmap(circleBitmap,0,0,null);
        }
    }
    private Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //新建一个bitmap
        Bitmap bitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //新建一个画布
        Canvas canvas=new Canvas(bitmap);
        //设置draw able的属性
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        //将draw able画到bitmap中
        drawable.draw(canvas);
        return bitmap;
    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        Bitmap outBitmap=Bitmap.createBitmap(width,height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(outBitmap);
        Paint paint=new Paint();
        // 设置抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        // 设置仿抖动
        paint.setDither(true);
        canvas.drawCircle(width/2,height/2,width/2,paint);
        // 取圆和Bitmap矩形的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再把原来的Bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap,0,0,paint);
        //  内存优化问题 Bitmap回收
        bitmap.recycle();
        bitmap = null;
        return outBitmap;
    }



    int i=0;
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }
}
