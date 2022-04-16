package com.example.saolei.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.example.saolei.MainActivity;
import com.example.saolei.R;

import java.text.DecimalFormat;

public class Myprogressbar extends ProgressBar {
    /**
     * 定义一个画笔
     */
    protected Paint mPaint = new Paint();
    /**
     * 设置默认的显示进度文字的颜色
     */
    protected int mTextColor = 0xff253688;
    /**
     * 设置显示默认的显示进度的文字的大小
     */
    protected int mTextSize = sp2px(16);

    /**
     * 设置默认的绘制进度条的高度
     */
    protected int mProgressBarHeight = dp2px(20);

    /**
     * 设置默认的绘制进度条颜色
     */
    protected int mBarColor = 0xffffcc00;
    /**
     * 未加载进度条的颜色
     */
    protected int mUnBarColor = 0xffe6e6e6;

    /**
     * 绘制进度条的实际宽度
     */
    protected int mRealWidth;

    public Myprogressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("myProgressbar","自定义导航栏调用1");
    }

    public Myprogressbar(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        Log.v("myProgressbar","自定义导航栏调用2");
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        /**
         * 定义画笔的初始位置
         */
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        /**
         * 计算加载进度的比例
         */
        float radio = getProgress() * 1.0f / getMax();
        /**
         * 计算已加载的进度
         */
        float progressPosX = (int) (mRealWidth * radio);
        /**
         * 定义进度上显示的文字信息
         */
        DecimalFormat df = new DecimalFormat("#.0");
        String text =  df.format(getProgress()/2) + "%";

        /**
         * 获取绘制文字的宽与高
         */

        mPaint.setTextSize(mTextSize);
        float textWidth = mPaint.measureText(text);//该方法并未真实的获取到字符串宽度，应该设置当前文字字体大小，才能正确获取
        float textHeight = (mPaint.descent() + mPaint.ascent())/2;
//        Log.v("masas",text+"    "+textWidth);
        /**
         * 判断绘制
         */
        if (progressPosX + textWidth > mRealWidth) {
            progressPosX = mRealWidth - textWidth;
        }


        /**
         * 绘制已加载的进度
         */

        mPaint.setColor(mBarColor);
        mPaint.setStrokeWidth(mProgressBarHeight);
        canvas.drawLine(0, 0, progressPosX, 0, mPaint);


//        Log.v("asds",progressPosX+"");


        mPaint.setColor(mTextColor);

        canvas.drawText(text,progressPosX, -textHeight, mPaint);

        /**
         * 绘制未加载的进度
         */
        float start = progressPosX + textWidth;
        mPaint.setColor(mUnBarColor);
        mPaint.setStrokeWidth(mProgressBarHeight);
        canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
//        Log.v("asds",start+"");
        /**
         * 绘制加载显示的文字
         */

        canvas.restore();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w-getPaddingLeft()-getPaddingRight();
        Log.v("Myprogressbar","当前宽度："+ mRealWidth);
    }
    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    /**
     * 设置已绘制的进度条的颜色
     *
     * @param mBarColor
     */
    public void setmBarColor(int mBarColor) {
        this.mBarColor = mBarColor;
    }

    /**
     * 设置过度条的高度
     *
     * @param mProgressBarHeight
     */
    public void setmProgressBarHeight(int mProgressBarHeight) {
        this.mProgressBarHeight = mProgressBarHeight;
    }

    /**
     * 设置指示进度条的显示文字的颜色
     *
     * @param mTextColor
     */
    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    /**
     * 设置指示进度条进度的显示文字的颜色
     *
     * @param mTextSize
     */
    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    /**
     * 设置未加载进度条处的进度颜色
     *
     * @param mUnBarColor
     */
    public void setmUnBarColor(int mUnBarColor) {
        this.mUnBarColor = mUnBarColor;
    }


    private  void  initFunction(){

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);


    }
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        /**
         * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
         * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
         * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
         */

        /**
         * 三种测量模式
         * UNSPECIFIED：父布局没有给子布局任何限制，子布局可以任意大小。
         * EXACTLY：父布局决定子布局的确切大小。不论子布局多大，它都必须限制在这个界限里。
         * AT_MOST：子布局可以根据自己的大小选择任意大小。
         */

        /**
         * 获取测量宽度大小
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        /**
         * 对高度进度测量
         */
        int height = 0;

        /**
         * 获取高度的测量模式
         */
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        /**
         * 获取测量高度的大小
         */
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 判断如果是非精准度的，那么就进行测量大小的重新设定
         */
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            /**
             * 获取显示加载进度的显示文本的宽度与高度
             */
            float textHeight = (mPaint.descent() - mPaint.ascent());
            /**
             * 计算测量的高度
             */
            height = (int) (getPaddingTop() + getPaddingBottom() + Math.max(
                    mProgressBarHeight, Math.abs(textHeight)));
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, specSize);
            }
        }
        /**
         * 设置测量
         */
        setMeasuredDimension(width, height);

        /**
         * 获取实际需要绘制进度条的宽度
         */
        mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 获取自定义属性操作
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void initFunction(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.Myprogressbar);

        mTextColor = attributes.getColor(R.styleable.Myprogressbar_textProColor, 0xffabc00);
    }
}
