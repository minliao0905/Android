package com.example.myemo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void insertDrawable(int id) {
        final SpannableString ss = new SpannableString("easy");
        //得到drawable对象，即所有插入的图片
        Drawable d = getResources().getDrawable(id);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //用这个drawable对象代替字符串easy
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        //包括0但是不包括"easy".length()即：4。[0,4)
        ss.setSpan(span, 0, "easy".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        append(ss);
    }
//    private TextPaint mTextPaint = new TextPaint();
//    public void setTypeface(@Nullable Typeface tf,   int style) {
//        if (style > 0) {
//            if (tf == null) {
//                tf = Typeface.defaultFromStyle(style);
//            } else {
//                tf = Typeface.create(tf, style);
//            }
//
//            setTypeface(tf);
//            // now compute what (if any) algorithmic styling is needed
//            int typefaceStyle = tf != null ? tf.getStyle() : 0;
//            int need = style & ~typefaceStyle;
//            mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
//            mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
//        } else {
//            setTypeface(tf);
//            mTextPaint.setFakeBoldText(false);
//            mTextPaint.setTextSkewX(0);
//        }
//    }

}