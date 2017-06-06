# Android-Carousel
Android使用ViewPage原生轮播图的实现
其实实现了内存了的优化，及轮播图的生命周期随Activity的生命周期变化而变化，所以Demo中有一个跳转的按钮用来测试在其他界面的时候轮播图是否还在使用，
因为轮播使用了Handler的机制，所以如果不优化内存，那么即使程序退出，Handel依然在使用，会造成内存泄露
可以直接在xml中用APP定义轮播图的属性
 <!-- 选中的点的颜色或者图片-->
       dotIndicatorFocus
       未选中的点的颜色或者图片-->
       dotIndicatorNormal
        <!-- 点的大小-->
        dotSize
        <!-- 点的间距-->
        dotDistance
        <!-- 点的位置-->
      dotGravity
            left
           cente
            right
    
        <!-- 底部的颜色 -->
       backgroundColor
        <!-- 滑动时间间隔 -->
      rollSpeed
        <!-- 动画速率 -->
       duration
        <!-- 图片高度-->
        heightProportion
        <!-- 图片宽度-->
      widthProportion
