package com.example.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class Card extends FrameLayout {

    public Card(Context context) {

        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
        setNum(0);

    }


    private int num = 0;
    private String[] colorlist =  {"#E6C0C0","#A4A8D4","#FFF3B8","#E5C1CD","#6C9BD2","#FAC559","#E6EB94","#A3D6CC","#ffcc33","#66ff66","#B4EEB4"};


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num<=0) {
            label.setText("");
            label.setBackgroundColor(0x33ffffff);
        }else{
            label.setText(num+"");
            label.setBackgroundColor(Color.parseColor(colorlist[num%11]));
        }
    }

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    private TextView label;
}
