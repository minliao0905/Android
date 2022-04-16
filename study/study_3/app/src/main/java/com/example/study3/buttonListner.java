package com.example.study3;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class buttonListner implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.lay1);
        linearLayout.setBackgroundColor(Color.parseColor("#FF3700B3"));//这样才可以
    }
}
