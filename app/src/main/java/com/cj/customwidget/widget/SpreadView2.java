package com.cj.customwidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.cj.customwidget.R;

import java.util.ArrayList;
import java.util.List;

public class SpreadView2 extends View {
    private Paint centerPaint; //中心圆paint
    private int radius = 100; //中心圆半径
    private Paint spreadPaint; //扩散圆paint
    private float centerX;//圆心x
    private float centerY;//圆心y
    private int distance = 5; //每次圆递增间距
    private int maxRadius = 80; //最大圆半径
    private int delayMilliseconds = 33;//扩散延迟间隔，越大扩散越慢
    private int centerColor = Color.parseColor("#ff0000");
    private int spreadColor =Color.parseColor("#2563ff");
    private List<Integer> spreadRadius = new ArrayList<>();//扩散圆层级数，元素为扩散的距离
    private List<Integer> alphas = new ArrayList<>();//对应每层圆的透明度
    private boolean isShowAnim = true;
    private Bitmap mBitmap;
    private float imgX;
    private float imgY;
    private onViewClick viewClick;

    public SpreadView2(Context context) {
        super(context);
        initView(context, null);
    }

    public SpreadView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SpreadView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
//        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpreadView);
//            radius = a.getInt(R.styleable.SpreadView_spread_radius, radius);
//            maxRadius = a.getInt(R.styleable.SpreadView_spread_max_radius, maxRadius);
//            centerColor = a.getColor(R.styleable.SpreadView_spread_center_color, getResources().getColor(R.color.colorPrimary));
//            spreadColor = a.getColor(R.styleable.SpreadView_spread_spread_color, getResources().getColor(R.color.half_c2563ff));
//            a.recycle();
//        }
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        centerPaint = new Paint();
        centerPaint.setColor(centerColor);
        centerPaint.setAntiAlias(true);
        //最开始不透明且扩散距离为0
        alphas.add(255);
        spreadRadius.add(0);
        spreadPaint = new Paint();
        spreadPaint.setAntiAlias(true);
        spreadPaint.setAlpha(255);
        spreadPaint.setColor(spreadColor);
    }

    public interface onViewClick {
        void onClick();
    }

    public void setOnViewClick(onViewClick viewClick) {
        this.viewClick = viewClick;
    }

    public void resumeAnim() {
        isShowAnim = true;
        postInvalidate();
    }

    public void stopAnim() {
        isShowAnim = false;
    }

    public boolean isShowAnim() {
        return isShowAnim;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //计算点击位置距离圆心距离
            double distance = Math.sqrt(Math.pow(Math.abs(event.getX()-centerX),2)+Math.pow(Math.abs(event.getY()-centerY),2));
            if (distance<radius){//在园内
                Log.d("lucas","inner");
            }else {
                Log.d("lucas","out");
            }
            return super.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh); //圆心位置
        centerX = w / 2;
        centerY = h / 2;
        imgX = centerX - mBitmap.getWidth() / 2;
        imgY = centerY - mBitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < spreadRadius.size(); i++) {
            int alpha = alphas.get(i);
            spreadPaint.setAlpha(alpha);
            int width = spreadRadius.get(i);
            //绘制扩散的圆
            canvas.drawCircle(centerX, centerY, radius + width, spreadPaint);
            //每次扩散圆半径递增，圆透明度递减
            if (alpha > 0 && width < 300) {
                alpha = alpha - distance > 0 ? alpha - distance : 1;
                alphas.set(i, alpha);
                spreadRadius.set(i, width + distance);
            }
        }
        //当最外层扩散圆半径达到最大半径时添加新扩散圆
        if (spreadRadius.get(spreadRadius.size() - 1) > maxRadius) {
            spreadRadius.add(0);
            alphas.add(255);
        }
        //超过8个扩散圆，删除最先绘制的圆，即最外层的圆
        if (spreadRadius.size() >= 8) {
            alphas.remove(0);
            spreadRadius.remove(0);
        }
        //中间的圆
        canvas.drawCircle(centerX, centerY, radius, centerPaint);
        //TODO 可以在中间圆绘制文字或者图片
        canvas.drawBitmap(mBitmap, imgX, imgY, new Paint());
        //延迟更新，达到扩散视觉差效果
        if (isShowAnim)
            postInvalidateDelayed(delayMilliseconds);
    }

}