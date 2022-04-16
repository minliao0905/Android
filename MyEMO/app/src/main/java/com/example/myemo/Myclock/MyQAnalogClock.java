package com.example.myemo.Myclock;

import android.util.AttributeSet;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.TimeZone;
import com.example.myemo.R;

public class MyQAnalogClock extends View {
    //时钟盘，分针、秒针、时针对象

    Bitmap mBmpDial;
    Bitmap mBmpHour;
    Bitmap mBmpMinute;
    Bitmap mBmpSecond;

    BitmapDrawable bmdHour;
    BitmapDrawable bmdMinute;
    BitmapDrawable bmdSecond;
    BitmapDrawable bmdDial;

    Paint mPaint;

    Handler tickHandler;

    int mWidth;
    int mHeigh;
    int centerX;
    int centerY;

    int availableWidth = GetCardWidth();
    int availableHeight = GetCardWidth();
    String[] myColor = {"#FF3700B3","#FF03DAC5","#FF03B4A5"};
    private String sTimeZoneString;


    public MyQAnalogClock(Context context,AttributeSet attr)
    {
        this(context,"GMT+8：00");

    }
    public MyQAnalogClock(Context context, String sTime_Zone) {
        super(context);
        sTimeZoneString = sTime_Zone;

        centerX = availableWidth / 2;
        centerY = availableHeight / 2;

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);   //设置画笔空心
        run();
    }

    public void run() {
        tickHandler = new Handler();
        tickHandler.post(tickRunnable);
    }

    private Runnable tickRunnable = new Runnable() {
        public void run() {
            postInvalidate();
            tickHandler.postDelayed(tickRunnable, 1000);
        }
    };

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Calendar cal = Calendar.getInstance(TimeZone
                .getTimeZone(sTimeZoneString));
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        float hourRotate = hour * 30.0f + minute / 60.0f * 30.0f;
        float minuteRotate = minute * 6.0f;
        float secondRotate = second * 6.0f;
        float cy = centerY;
        float cx = centerX;
        float sx ;
        float sy ;
        boolean scaled = false;

        if (availableWidth < mWidth || availableHeight < mHeigh) {
            scaled = true;
            float scale = Math.min((float) availableWidth / (float) mWidth,
                    (float) availableHeight / (float) mHeigh);
            canvas.save();
            canvas.scale(scale, scale, centerX, centerY);
        }


      //画线  和圆圈
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE); //设置填充样式
        mPaint.setColor(Color.parseColor(myColor[0])); //设置画笔颜色
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(centerX,centerX, mWidth/2,mPaint);
canvas.restore();
canvas.save();
        //给表画线
        mPaint.setStyle(Paint.Style.FILL); //设置填充样式

        mPaint.setStrokeWidth(10); //设置画笔宽度
        mPaint.setColor(Color.parseColor(myColor[1]));
        for(int i=0;i<12;i++){
            float lineRotate = i*30.0f;
            canvas.rotate(lineRotate,centerX, centerY);
            canvas.drawLine(50,cx,100,cy,mPaint);
        }
        canvas.restore();



        //画时钟
        canvas.save();
         sx = centerX/2-50;
         sy = centerY/2-50;
        mPaint.setStyle(Paint.Style.FILL); //设置填充样式
        mPaint.setColor(Color.parseColor(myColor[0])); //设置画笔颜色
        mPaint.setStrokeWidth(50); //设置画笔宽度
        canvas.rotate(hourRotate, centerX, centerY);
        canvas.drawLine(cx,cy,  cx-sx,  cy-sy,mPaint);

        canvas.restore();


        //画分针
        canvas.save();
          sx = centerX/2-40;
          sy = centerY/2-40;
        mPaint.setColor(Color.parseColor(myColor[2] )); //设置画笔颜色
        mPaint.setStrokeWidth(40); //设置画笔宽度
        canvas.rotate(minuteRotate , centerX, centerY);
        canvas.drawLine(cx,cy,  cx-sx,  cy-sy,mPaint);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        //添加画圆
        canvas.drawCircle(centerX,centerX, cx-sx+10,mPaint);
//        mPaint.setStrokeWidth(20); //设置画笔宽度
//        mPaint.setColor(Color.parseColor(myColor[2]));
//        for(int i=0;i<12;i++){
//            float lineRotate = i*30.0f;
//            canvas.rotate(lineRotate,centerX, centerY);
//            canvas.drawLine(centerX-(cx-sx-10),cx,centerX/2+10,cy,mPaint);
//        }
        canvas.restore();


        //画秒针
        canvas.save();
        sx = centerX/2-10;
        sy = centerY/2-10;
        mPaint.setColor(Color.parseColor("#FFBB86FC")); //设置画笔颜色
        mPaint.setStrokeWidth(30); //设置画笔宽度
        canvas.rotate(secondRotate, centerX, centerY);
        canvas.drawLine(cx,cy,  cx-sx,  cy-sy,mPaint);

        canvas.restore();
        if (scaled) {
            canvas.restore();
        }
    }

    private int GetCardWidth()
    {
        //屏幕信息的对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        //获取屏幕信息
        int  cardWidth = displayMetrics.widthPixels;
        return cardWidth;
    }
}
 