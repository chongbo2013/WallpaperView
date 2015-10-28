package com.example.administrator.cleanviewactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 移动气泡
 * 2015年10月28日 10:07:30
 */
public class WallpaperView extends View implements ValueAnimator.AnimatorUpdateListener {
    /**
     * 动画改变的属性值
     */
    private float phase = 0f;
    /**
     * 小球集合
     */
    private List<Fllower> fllowers1 = new ArrayList<Fllower>();

    /**
     * 动画播放的时间
     */
    private int time = 1000;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 测量路径的坐标位置
     */
    private PathMeasure pathMeasure = null;
    /**
     * 区域
     */
    private PathMeasure pathRectMeasure = null;
    /**
     * 气泡的数量
     */
    private int fllowerCount = 10;
    private int paddingLeft=0, paddingTop=0,paddingRight=0,paddingBottom=0;
    private int contentWidth=0,contentHeight=0;
    Path mRectPath=new Path();
    private int[] radius=new int[]{7,8,9,10,12,13,14,15};
    private int[] colors=new int[]{0x7Fe51c23,0x7Fe91e63,0x7f9c27b0,0x7f673ab7,0x7f3f51b5,0x7f5677fc,0x7f5677fc,0x7f03a9f4,0x7f00bcd4,0x7f009688,0x7f259b24,0x7f8bc34a,0x7fcddc39,0x7fffc107,0x7fff9800,0x7fff5722,0x7f795548,0x7f9e9e9e,0x7f607d8b};


    public WallpaperView(Context context) {
        super(context);
        init(null, 0);
    }

    public WallpaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WallpaperView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        pathMeasure = new PathMeasure();
        pathRectMeasure= new PathMeasure();
    }

    public void initSize(int width,int height){

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        contentWidth = width - paddingLeft - paddingRight;
        contentHeight = height - paddingTop - paddingBottom;
        initStartPoint();//初始化区域
        initFllwers();//初始化路径

    }



    public void initStartPoint(){
        mRectPath.reset();
        RectF mRectF=new RectF(paddingLeft,paddingTop,paddingLeft+contentWidth,paddingTop+contentHeight);
        mRectPath.addRect(mRectF, Path.Direction.CW);
        pathRectMeasure.setPath(mRectPath, false);
    }

    /**
     * 随机从边缘获取一个点，来做为贝塞尔曲线的起始点
     * @return
     */
    public float[] getStartPathRandom(){
        float[] pos=new float[2];
        float length=pathRectMeasure.getLength();
        Random random = new Random();
        float value=random.nextFloat()*0.5f;
        pathRectMeasure.getPosTan(value * length, pos, null);
        return pos;
    }

    /**
     * 随机从边缘获取一个点，来做为贝塞尔曲线的起始点
     * @return
     */
    public float[] getPathRandom(float value){
        float[] pos=new float[2];
        float length=pathRectMeasure.getLength();
        pathRectMeasure.getPosTan(value * length, pos, null);
        return pos;
    }

    /**
     * 随机从边缘获取一个点，来做为贝塞尔曲线的起始点
     * @return
     */
    public float getRandom(){
        Random random = new Random();
        return random.nextFloat();
    }

    public int getRandomColor(){
        int ram = (int)(Math.random()*colors.length);
        return colors[ram];
    }
    public int getRandomRadius(){
        int ram = (int)(Math.random()*radius.length);
        return radius[ram];
    }

    /**
     * 随机从边缘获取一个点，来做为贝塞尔曲线的起始点
     * @return
     */
    public float[] getEndPathRandom(){
        float[] pos=new float[2];
        float length=pathRectMeasure.getLength();
        Random random = new Random();
        float value=random.nextFloat()*1.0f+0.5f;
        pathRectMeasure.getPosTan(value * length, pos, null);
        return pos;
    }
    //初始化，每个气泡的路径曲线
    public void initFllwers(){
        fllowers1.clear();
        for(int i=0;i<fllowerCount;i++){
            Fllower fllower=new Fllower();
            Path fllowerPath=new Path();

            fllower.setFllowerColor(getRandomColor());
            fllower.setWidth(getRandomRadius());
            float[] start=null;
            float[] end=null;
            float random=getRandom();
            start=getPathRandom(random);
            if(random>0.5f){
                end=getStartPathRandom();
            }else{
                end=getStartPathRandom();
            }

            float[] control=new float[2];//控制点
            control[0]=getWidth()/2;
            control[1]=getHeight()/2;
            //移动到开始点
            fllowerPath.moveTo(start[0],start[1]);
            fllowerPath.quadTo(control[0],control[1],end[0],end[1]);
            fllower.setPath(fllowerPath);
            fllowers1.add(fllower);
        }

    }

    /**
     *
     * @param canvas
     * @param
     */
    private void drawFllower(Canvas canvas) {
        for (Fllower fllower : fllowers1) {
            float[] pos = new float[2];
//            canvas.drawPath(fllower.getPath(),mPaint);
            pathMeasure.setPath(fllower.getPath(), false);
            pathMeasure.getPosTan(pathMeasure.getLength() * fllower.getValue(), pos, null);
            mPaint.setColor(fllower.getFllowerColor());
            canvas.drawCircle(pos[0], pos[1], fllower.getWidth(), mPaint);

        }
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFllower(canvas);
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.


    }


    private int repeatCount=5;
    private ObjectAnimator mAnimator1;
    public void startAnimation() {
        if(repeatCount<=0){
            repeatCount=5;
            return;
        }
            initFllwers();
            mAnimator1 = ObjectAnimator.ofFloat(this, "phase", 0f,
                    1f);
            mAnimator1.setDuration(time);
            mAnimator1.addUpdateListener(this);
//            mAnimator1.setRepeatMode(Animation.REVERSE);
//            mAnimator1.setRepeatCount(2);
            mAnimator1.setInterpolator(new LinearInterpolator());
            mAnimator1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    --repeatCount;
                    startAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

        mAnimator1.start();

    }



    /**
     * 跟新小球的位置
     *
     * @param value
     * @param fllowers
     */
    private void updateValue(float value, List<Fllower> fllowers) {
        for (Fllower fllower : fllowers) {
            fllower.setValue(value);
        }
    }

    /**
     * 动画改变回调
     */
    @Override
    public void onAnimationUpdate(ValueAnimator arg0) {
        int mode=arg0.getRepeatMode();
        updateValue(getPhase(), fllowers1);
        invalidate();
    }

    public float getPhase() {
        return phase;
    }

    public void setPhase(float phase1) {
        this.phase = phase1;
    }


}
