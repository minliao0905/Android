package com.example.myemo.Log.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myemo.R;

import org.w3c.dom.Text;

public class Emocard extends FrameLayout  {
    private ImageButton label;
    public Emocard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public Emocard(Context context){
        super(context);
        label = new ImageButton(getContext());
//        存在设置背景异常问题
        label.setBackgroundColor(Color.parseColor("#ffffff"));

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
    }

    public void setBackground(String color) {
        label.setBackgroundColor(Color.parseColor(color));
    }


}
