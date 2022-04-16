package com.example.myemo.Myclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

public class MyQAnalogColck2 extends View {

    private Paint hourPaint;//绘制时针的画笔
    private Paint minPaint;//绘制分针的画笔
    private Paint secPaint;//绘制秒针的画笔
    int width = 350;//钟表的圆心X轴坐标
    int heigth = 350;//钟表的圆心Y轴坐标
    int radius = 300;//钟表的半径
    public MyQAnalogColck2(Context context) {
        super(context);

    }

    public MyQAnalogColck2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    public void setCenter(){
//        //屏幕信息的对象
//        DisplayMetrics displayMetrics;
//        displayMetrics = getResources().getDisplayMetrics();
//        width = displayMetrics.widthPixels/2;
//        heigth = 150;
//        width = 150;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK);
        setCenter(); //将画笔置于屏幕中心
        canvas.drawCircle(width, heigth, radius+10, paint);

        //画刻度
        for (int i = 1; i < 13; i++) {
            canvas.save();//保存当前的状态
            //1:旋转的角度 2.旋转中心的X轴坐标 3.旋转中心的Y轴坐标
            canvas.rotate(i * 30, width, heigth);
            //画刻度线 相当于250,50,250,65
            canvas.drawLine(width, heigth - radius, width, heigth - radius + 15, paint);
            canvas.restore();//返回初始保存的状态
        }


        //画小刻度
        for (int i = 0; i < 60; i++) {
            canvas.save();//保存当前的状态
            //1:旋转的角度 2.旋转中心的X轴坐标 3.旋转中心的Y轴坐标
            canvas.rotate(i * 6, width, heigth);
            //画刻度线 相当于250,50,250,55
            canvas.drawLine(width, heigth - radius, width, heigth - radius + 5, paint);
            canvas.restore();//返回初始保存的状态
        }

        //画钟表的心
        Paint centerPaint = new Paint();
        centerPaint.setColor(Color.RED);
        centerPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width, heigth, 10, centerPaint);

        paint.setColor(Color.RED);
        paint.setTextSize(25);
        paint.setStrokeWidth(3);

        //写数字
        for (int i = 1; i < 13; i++) {
            canvas.save();//保存当前的状态
            //1:旋转的角度 2.旋转中心的X轴坐标 3.旋转中心的Y轴坐标
            canvas.rotate(i * 30, width, heigth);
            //写数字 相当于250,50,250,65
            canvas.drawText(i + "", width - 5, heigth - radius + 50, paint);
            canvas.restore();//返回初始保存的状态
        }


        //时针画笔样式
        hourPaint = new Paint();
        hourPaint.setColor(Color.BLACK);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeWidth(7);

        //分针画笔样式
        minPaint = new Paint();
        minPaint.setColor(Color.GRAY);
        minPaint.setStyle(Paint.Style.FILL);
        minPaint.setStrokeWidth(6);

        //秒针画笔样式
        secPaint = new Paint();
        secPaint.setColor(Color.BLUE);
        secPaint.setStyle(Paint.Style.FILL);
        secPaint.setStrokeWidth(4);

        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        //画时针
        canvas.save();
        //画布的旋转,参数1:旋转的角度 2:围绕着旋转的点进行旋转的X轴坐标 3:Y轴坐标
        //第一个参数:就比如4:30 时针的偏移角度
        // 4*30=120表示四点的时候时针在钟表的这个角度的位置
        // 30分/60表示占据的百分比 然后再*30 就是三十分钟占一刻(30度)的多少角度
        canvas.rotate(hour * 30 + minute / 60 * 30, width, heigth);
        canvas.drawLine(width, heigth + 40, width, heigth - 140, hourPaint);
        canvas.restore();

        //画分针
        canvas.save();
        //分针每走一分都走6度
        canvas.rotate(minute * 6, width, heigth);
        canvas.drawLine(width, heigth + 30, width, heigth - 160, minPaint);
        canvas.restore();

        //画秒针
        canvas.save();
        //时针每走一分都走6度
        canvas.rotate(second * 6, width, heigth);
        canvas.drawLine(width, heigth + 20, width, heigth - 190, secPaint);
        canvas.restore();

        invalidate();//重复调用ondraw的方法,不断的绘制,使用时钟呈现出走动的效果
    }
}