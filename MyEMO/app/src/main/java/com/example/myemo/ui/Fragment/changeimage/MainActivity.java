package com.example.myemo.ui.Fragment.changeimage;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myemo.ui.Fragment.changeimage.Makenum.GameView;
import com.example.myemo.R;
import com.example.myemo.ui.main.HomeActivity;

public class MainActivity extends Activity {

    public static MainActivity mainActivity = null;
    private int score = 0;
    private static TextView tvScore;
    private static TextView tvHScore;
    public GameView gmview ;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_mainnum);
        tvScore = (TextView) findViewById(R.id.tvScorenum);
        tvHScore = (TextView) findViewById(R.id.tvHScorenum);
        gmview = findViewById(R.id.gameView);
//        ImageButton rebtn = findViewById(R.id.restartbtn);
        ImageView gifView1 =  findViewById(R.id.restartbtnnum);
        Glide.with(this).load(R.drawable.s5).into(gifView1);
        gifView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmview.startGame();
            }
        });

    }


    public  static void setTvScore(int movecount){
        tvScore.setText( movecount +"次");
    }



    public int getScore(){
        return score;
    }


}
