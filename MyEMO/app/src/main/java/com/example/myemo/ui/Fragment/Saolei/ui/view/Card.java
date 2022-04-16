package com.example.myemo.ui.Fragment.Saolei.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class Card extends FrameLayout {

    public Card(Context context, AttributeSet attrs) { // 构造函数
        super(context, attrs);
        label = new TextView(getContext());
        label.setBackgroundColor(Color.parseColor("#BEBEBE"));
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(5, 5, 0, 0);
        addView(label, lp);
    }



    private int num = -2;
    private String[] colorlist =  {"#E6C0C0","#A4A8D4","#FFF3B8","#E5C1CD","#6C9BD2","#FAC559","#E6EB94","#A3D6CC","#ffcc33","#66ff66","#B4EEB4"};

    public int getNum() {
        return num;
    }
    public void setTextsize(int textsize){
        label.setTextSize(textsize);
    }
    public void setNum(int num) {
        this.num = num;
        if (num == 0) {
            label.setText("0");
            label.setBackgroundColor(Color.parseColor("#8B8989"));
        } else if (num == -1) {
            label.setText("-1");
            label.setBackgroundColor(Color.parseColor("#CD0000"));
        } else if(num>0){
            label.setText(num + "");
            label.setBackgroundColor(Color.parseColor(colorlist[num % 11]));
        }else if(num==-2){
            label.setText("");
            label.setBackgroundColor(Color.parseColor("#BEBEBE"));
        }
    }
    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    private TextView label;
}
