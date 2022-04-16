package com.example.changeimage.MakeImg;


import static com.example.changeimage.StartActivity.num;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;


public class ImgCard extends FrameLayout {

    private ImageView label;
    private TextView numlabel;

    private int index = 0;
    public ImgCard(Context context) {

        super(context);
        label = new ImageView(getContext());
        numlabel = new TextView(getContext());
        label.setBackgroundColor(0x33ffffff);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
//        //设置长按监听 显示当前图片的标号
//        this.setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(getContext(),"当前碎片为第"+index + "个",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        会导致无法监听滑动，失去焦点

    }


    public void setBm(Bitmap bm ,int num) {

        if (num<=0) {
            label.setBackgroundColor(0xffffff);
            label.setImageBitmap(null);    //要注意设置为null不然不改变

        }else{
          label.setImageBitmap(bm);
//          label.setAlpha(60);
        }
        index = num;
    }
    public void setbackgAlpha(int num){
        label.setAlpha(num);   //当碎片对应位置正确时表示透明度确定
    }

  public int getIndex(){
       return  index;
  }
}
