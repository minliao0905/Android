package com.example.myemo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myemo.Splash.SplashActivity;
import com.example.myemo.ui.main.HomeActivity;
import com.example.myemo.ui.main.LogActivity;
import com.example.myemo.view.DragFloatActionButton;

import java.util.Calendar;

public class MainActivity extends Activity  {
    String sTimeZoneString = "GMT+8：00";    //时区定义
    Handler timeckHandler;
//    TextView mytimev ;
    private DragFloatActionButton Game2048 = null;
    private DragFloatActionButton Gamepingtu = null;
    private DragFloatActionButton Gamesaolei = null;
    private Button Logbtn = null;
    private ImageButton setbgBtn;
    private ConstraintLayout main_bak =  null;
    /**
     * 上次点击返回键的时间
     */
    private long lastBackPressed;
    /**
     * 上次点击返回键的时间
     */
    private static final int QUIT_INTERVAL = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(fade);
    }
    public void init(){
       Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//        mytimev = findViewById(R.id.myclocltime);
        Game2048 = findViewById(R.id.Game2048);
        Game2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转至游戏主界面
                intent.putExtra("currentitem",0);
                startActivity(intent);
            }
        });
        Gamepingtu = findViewById(R.id.Gamepingtu);
        Gamepingtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转至游戏主界面
                intent.putExtra("currentitem",1);
                startActivity(intent);
            }
        });
        Gamesaolei = findViewById(R.id.Gamesaolei);
        Gamesaolei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转至游戏主界面
                intent.putExtra("currentitem",2);
                startActivity(intent);
            }
        });
        Logbtn = findViewById(R.id.Logbtn);
        Logbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Activity转场动画
                startActivity(new Intent(MainActivity.this, LogActivity.class), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

        setbgBtn = findViewById(R.id.top_m1);
        setbgBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
//                Log.v("12","123");
                LinearLayout btnll = findViewById(R.id.randomgamebtn);
                if(btnll.getVisibility()==View.INVISIBLE){
                    btnll.setVisibility(View.VISIBLE);
                }else {
                    btnll.setVisibility(View.INVISIBLE);
                }

            }
        });
        main_bak = findViewById(R.id.main_back);
        main_bak.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            private int[] back={R.drawable.button1_style,R.drawable.button2_style,R.drawable.button3_style};
            int  count = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) { // 加此判断是为了解决当用户向斜方向滑动时程序应如何判断的问题

                            if (offsetX < -5) {
                                count += 4;
                                count %= 3;
                                // System.out.println("left");
                            } else if (offsetX > 5) {
                                count += 2;
                                count %= 3;
                                // System.out.println("right");
                            }
                            main_bak.setBackground(getDrawable(back[count]));
                        }
                        break;
                }
                return true;
            }
        });
        run();

    }
    public void run() {
        timeckHandler = new Handler();
        timeckHandler.post(tickRunnable);
    }

    private Runnable tickRunnable = new Runnable() {
        public void run() {
//            Calendar cal = Calendar.getInstance( );
//            int hour = cal.get(Calendar.HOUR_OF_DAY);
//            int minute = cal.get(Calendar.MINUTE);
//            int second = cal.get(Calendar.SECOND);
//            mytimev.setText(hour+"时 "+minute+"分 ");
            timeckHandler.postDelayed(tickRunnable, 1000);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (keyCode == KeyEvent.KEYCODE_BACK) { // 表示按返回键 时的操作
//                long backPressed = System.currentTimeMillis();
//                if (backPressed - lastBackPressed > QUIT_INTERVAL) {
//                    lastBackPressed = backPressed;
//                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    moveTaskToBack(false);
////                    MyApplication.closeApp(this);
//                    finish();
//                }
//            }
//        }
//        return false;
//    }
}