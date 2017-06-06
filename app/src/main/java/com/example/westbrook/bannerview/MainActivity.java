package com.example.westbrook.bannerview;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.westbrook.bannerview.Adapter.BannerAdapter;
import com.example.westbrook.bannerview.MyView.BannerLayout;
import com.example.westbrook.bannerview.MyView.BannerView;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BannerLayout mLayout;
    private int[] pics=new int[]{R.drawable.pic_01,R.drawable.pic_02,R.drawable.pic_03};
    BannerAdapter adapter=new BannerAdapter() {
        @Override
        public View getView(int position) {
            ImageView view=new ImageView(MainActivity.this);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(pics[position]);
            Log.d(TAG, "getView: "+position);
            return view;
        }

        @Override
        public int getCount() {
            return 3;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout= (BannerLayout) findViewById(R.id.banner_layout);
       mLayout.setBannerAdapter(adapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OtherActivity.class));
            }
        });

    }

}
